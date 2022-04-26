package com.thundernorain.fillwordgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thundernorain.fillwordgame.domain.interactor.CheckWordInteractor
import com.thundernorain.fillwordgame.domain.interactor.CloseConnectionInteractor
import com.thundernorain.fillwordgame.domain.interactor.GameFieldInteractor
import com.thundernorain.fillwordgame.domain.mapper.CheckWordResponseMapper
import com.thundernorain.fillwordgame.domain.mapper.FillwordResponseMapper
import com.thundernorain.fillwordgame.domain.mapper.GameFieldItemsToWordMapper
import com.thundernorain.fillwordgame.domain.mapper.TimeLongMapper
import com.thundernorain.fillwordgame.domain.model.fillword_game.Difficulty
import com.thundernorain.fillwordgame.domain.model.ui_model.CheckWordResultUiModel
import com.thundernorain.fillwordgame.domain.model.ui_model.GameFieldItem
import com.thundernorain.fillwordgame.domain.model.ui_model.GameScreenState
import com.thundernorain.fillwordgame.presentation.extensions.launchPeriodicAsync
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class GameViewModel(
    private val checkWordInteractor: CheckWordInteractor,
    private val gameFieldInteractor: GameFieldInteractor,
    private val closeConnectionInteractor: CloseConnectionInteractor,
    private val checkWordResponseMapper: CheckWordResponseMapper,
    private val fillwordResponseMapper: FillwordResponseMapper,
    private val timeLongMapper: TimeLongMapper,
    private val gameFieldItemsToWordMapper: GameFieldItemsToWordMapper,
) : ViewModel() {

    private val mutableRemainingTime = MutableStateFlow("")
    val remainingTime: StateFlow<String>
        get() = mutableRemainingTime

    private val mutableWordsStats = MutableStateFlow("")
    val wordsStats: StateFlow<String>
        get() = mutableWordsStats

    private val mutableGameField = MutableStateFlow<List<GameFieldItem>>(emptyList())
    val gameField: StateFlow<List<GameFieldItem>>
        get() = mutableGameField

    private val mutableCheckedGameFieldItems = MutableStateFlow<List<GameFieldItem>>(emptyList())
    val checkedGameFieldItems: StateFlow<List<GameFieldItem>>
        get() = mutableCheckedGameFieldItems

    private val mutableGameScreenState = MutableStateFlow(GameScreenState.IDLE)
    val gameScreenState: StateFlow<GameScreenState>
        get() = mutableGameScreenState

    private var correctWordsCountValue = 0
    private var allWordsCountValue = 0

    private var timerJob: Job? = null
    private var timerSeconds = 0L
        set(value) {
            field = value
            onTimerChange(value)
        }

    fun onViewCreated(difficulty: Difficulty) {
        loadGameData(difficulty)
    }

    fun onCheckWord(selectedLetters: List<GameFieldItem>) {
        viewModelScope.launch {
            try {
                val word = gameFieldItemsToWordMapper.map(selectedLetters)
                val response = checkWordInteractor.invoke(word)
                handleCheckWordResult(
                    selectedLetters,
                    checkWordResponseMapper.map(response).result
                )
            } catch (exception: Exception) {
                mutableGameScreenState.value = GameScreenState.SERVER_ERROR
            }
        }
    }

    fun onContinueGame() {
        mutableGameScreenState.value = GameScreenState.IDLE
    }

    fun onViewDestroyed() {
        viewModelScope.launch {
            closeConnectionInteractor.invoke()
            timerJob?.cancel()
        }
    }

    private fun loadGameData(difficulty: Difficulty) {
        viewModelScope.launch {
            try {
                mutableGameScreenState.value = GameScreenState.LOADING
                val response = gameFieldInteractor.invoke(difficulty)
                val responseMapped = fillwordResponseMapper.map(response)
                mutableGameField.value = responseMapped.gameField
                allWordsCountValue = responseMapped.wordsCount
                timerSeconds = responseMapped.timeToSolve + 1
                setWordsStats()
                startTimer()
                mutableGameScreenState.value = GameScreenState.IDLE
            } catch (exception: Exception) {
                mutableGameScreenState.value = GameScreenState.SERVER_ERROR
            }
        }
    }

    private fun handleCheckWordResult(
        items: List<GameFieldItem>,
        result: CheckWordResultUiModel
    ) = when (result) {
        CheckWordResultUiModel.CORRECT -> {
            items.forEach {
                it.isCorrect = true
                it.isSelected = false
            }
            mutableCheckedGameFieldItems.value = items
            onCorrectWordReceived()
        }
        CheckWordResultUiModel.WRONG -> {
            items.forEach { it.isSelected = false }
            mutableCheckedGameFieldItems.value = items
            mutableGameScreenState.value = GameScreenState.INCORRECT_WORD
        }
        CheckWordResultUiModel.SERVER_ERROR -> {
            mutableGameScreenState.value = GameScreenState.SERVER_ERROR
        }
    }

    private fun startTimer() {
        timerJob = viewModelScope.launchPeriodicAsync(SECOND_IN_MILLIS) {
            timerSeconds--
        }
    }

    private fun onCorrectWordReceived() {
        correctWordsCountValue++
        if (correctWordsCountValue >= allWordsCountValue) {
            mutableGameScreenState.value = GameScreenState.WIN
        }
        setWordsStats()
    }

    private fun setWordsStats() {
        mutableWordsStats.value = "$correctWordsCountValue / $allWordsCountValue"
    }

    private fun onTimerChange(time: Long) {
        if (timerSeconds <= 0) {
            mutableGameScreenState.value = GameScreenState.TIME_OVER
            timerJob?.cancel()
        }
        mutableRemainingTime.value = timeLongMapper.toString(time)
    }

    companion object {
        private const val SECOND_IN_MILLIS = 1000L
    }
}