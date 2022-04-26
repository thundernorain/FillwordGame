package com.thundernorain.fillwordgame.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thundernorain.fillwordgame.R
import com.thundernorain.fillwordgame.databinding.FragmentGameBinding
import com.thundernorain.fillwordgame.domain.model.ui_model.GameFieldItem
import com.thundernorain.fillwordgame.domain.model.ui_model.GameScreenState
import com.thundernorain.fillwordgame.presentation.adapter.GameFieldAdapter
import com.thundernorain.fillwordgame.presentation.extensions.collectLatestOn
import com.thundernorain.fillwordgame.presentation.fragments.dialogs.GameStateDialog
import com.thundernorain.fillwordgame.presentation.viewmodel.GameViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class GameFragment : Fragment(R.layout.fragment_game) {

    private val viewModel by viewModel<GameViewModel>()
    private val binding by viewBinding(FragmentGameBinding::bind)

    private val navController by lazy { findNavController() }
    private val args: GameFragmentArgs by navArgs()

    private val gameFieldAdapter by lazy { GameFieldAdapter(::onGameFieldItemClick) }

    private val selectedLetters = mutableListOf<GameFieldItem>()
    private var lastSelectedLetterPosition = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewCreated(args.difficulty)
        observeData()
        initGameField()
        initOnClickListeners()
    }

    private fun observeData() = with (viewModel) {
        remainingTime.collectLatestOn(viewLifecycleOwner) { handleRemainingTime(it) }
        wordsStats.collectLatestOn(viewLifecycleOwner) { handleWordsStats(it) }
        gameField.collectLatestOn(viewLifecycleOwner) { handleGameField(it) }
        checkedGameFieldItems.collectLatestOn(viewLifecycleOwner) { handleCheckedWords(it) }
        gameScreenState.collectLatestOn(viewLifecycleOwner) { handleGameScreenState(it) }
    }

    private fun initOnClickListeners() = with (binding) {
        sendWordButton.setOnClickListener { onSendWordClick() }
        cancelChoosenLetterTextView.setOnClickListener { onCancelChosenLettersClick() }
        exitImageView.setOnClickListener {
            val action = GameFragmentDirections.actionGameFragmentToMenuFragment()
            navController.navigate(action)
        }
        restartImageView.setOnClickListener {
            val action = GameFragmentDirections.actionGameFragmentSelf(args.difficulty)
            navController.navigate(action)
        }
    }

    private fun handleGameScreenState(state: GameScreenState) = when (state) {
        GameScreenState.LOADING -> showLoading(true)
        GameScreenState.WIN -> navigateToResultScreen(getString(R.string.win_message))
        GameScreenState.TIME_OVER -> navigateToResultScreen(getString(R.string.time_over_message))
        GameScreenState.INCORRECT_WORD -> {
            showErrorDialog(getString(R.string.dialog_incorrect_word))
            showLoading(false)
            viewModel.onContinueGame()
        }
        GameScreenState.SERVER_ERROR -> {
            showErrorDialog(getString(R.string.server_unavailable))
            showLoading(false)
        }
        GameScreenState.IDLE -> showLoading(false)
    }

    private fun initGameField() {
        with (binding.gameFieldRecyclerView) {
            adapter = gameFieldAdapter
            layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
        }
    }

    private fun onGameFieldItemClick(item: GameFieldItem) {
        if (
            checkCanSelectLetter(item.position)
            && !item.isCorrect
        ) {
            item.isSelected = true
            lastSelectedLetterPosition = item.position
            selectedLetters.add(item)
            gameFieldAdapter.notifyItemChanged(item.position)
        }
    }

    private fun onCancelChosenLettersClick() {
        selectedLetters.forEach { item ->
            item.isSelected = false
            gameFieldAdapter.updateItem(item)
        }
        clearSelectedLetters()
    }

    private fun handleGameField(gameField: List<GameFieldItem>) {
        gameFieldAdapter.updateAllItems(gameField)
    }

    private fun handleWordsStats(wordsStats: String) {
        binding.wordsCountTextView.text = wordsStats
    }

    private fun handleRemainingTime(time: String) {
        binding.timeTextView.text = time
    }

    private fun handleCheckedWords(items: List<GameFieldItem>) {
        items.forEach { gameFieldAdapter.updateItem(it) }
    }

    private fun onSendWordClick() {
        viewModel.onCheckWord(selectedLetters.toList())
        clearSelectedLetters()
    }

    private fun checkCanSelectLetter(position: Int): Boolean {
        if (lastSelectedLetterPosition == -1) {
            return true
        }
        return when(abs(lastSelectedLetterPosition - position)) {
            SPAN_COUNT -> true
            1 -> true
            else -> false
        }
    }

    private fun clearSelectedLetters() {
        selectedLetters.clear()
        lastSelectedLetterPosition = -1
    }

    private fun navigateToResultScreen(title: String) {
        val action = GameFragmentDirections.actionGameFragmentToGameResultFragment(title)
        navController.navigate(action)
    }

    private fun showErrorDialog(title: String) {
        GameStateDialog.getInstance(title).show(childFragmentManager, GameStateDialog.TAG)
    }

    private fun showLoading(isShown: Boolean) {
        binding.progressBar.root.isVisible = isShown
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onViewDestroyed()
    }

    companion object {
        private const val SPAN_COUNT = 7
    }
}