package com.thundernorain.fillwordgame.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thundernorain.fillwordgame.R
import com.thundernorain.fillwordgame.databinding.FragmentDifficultyChooseBinding
import com.thundernorain.fillwordgame.presentation.model.Difficulty

class DifficultyChooseFragment : Fragment(R.layout.fragment_difficulty_choose) {

    private val binding by viewBinding(FragmentDifficultyChooseBinding::bind)
    private val navController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() = with(binding) {
        easyDifficultyButton.setOnClickListener {
            navigateToGame(Difficulty.EASY)
        }
        mediumDifficultyButton.setOnClickListener {
            navigateToGame(Difficulty.MEDIUM)
        }
        hardDifficultyButton.setOnClickListener {
            navigateToGame(Difficulty.HARD)
        }
        backArrowImageView.setOnClickListener {
            navController.navigateUp()
        }
    }

    private fun navigateToGame(difficulty: Difficulty) {
        val action = DifficultyChooseFragmentDirections
            .actionDifficultyChooseFragmentToGameFragment(difficulty)
        navController.navigate(action)
    }
}