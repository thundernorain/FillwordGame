package com.thundernorain.fillwordgame.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thundernorain.fillwordgame.R
import com.thundernorain.fillwordgame.databinding.FragmentDifficultyChooseBinding
import com.thundernorain.fillwordgame.domain.model.ui_model.DifficultyUiModel

class DifficultyChooseFragment : Fragment(R.layout.fragment_difficulty_choose) {

    private val binding by viewBinding(FragmentDifficultyChooseBinding::bind)
    private val navController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() = with(binding) {
        easyDifficultyButton.setOnClickListener {
            navigateToGame(DifficultyUiModel.EASY)
        }
        mediumDifficultyButton.setOnClickListener {
            navigateToGame(DifficultyUiModel.MEDIUM)
        }
        hardDifficultyButton.setOnClickListener {
            navigateToGame(DifficultyUiModel.HARD)
        }
        backArrowImageView.setOnClickListener {
            navController.navigateUp()
        }
    }

    private fun navigateToGame(difficulty: DifficultyUiModel) {
        val action = DifficultyChooseFragmentDirections
            .actionDifficultyChooseFragmentToGameFragment(difficulty)
        navController.navigate(action)
    }
}