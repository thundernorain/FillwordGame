package com.thundernorain.fillwordgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.thundernorain.fillwordgame.presentation.model.Difficulty
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GameViewModel : ViewModel() {

    private val mutableRemainingTime = MutableStateFlow("")
    val remainingTime: StateFlow<String>
        get() = mutableRemainingTime

    private val mutableCorrectWordsCount = MutableStateFlow("")
    val correctWordsCount: StateFlow<String>
        get() = mutableCorrectWordsCount

    private var correctWordsCountValue = 0
    private var allWordsCountValue = 0

    private lateinit var difficulty: Difficulty

    fun onViewCreated(difficulty: Difficulty) {
        this.difficulty = difficulty
    }
}