package com.thundernorain.fillwordgame.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thundernorain.fillwordgame.R
import com.thundernorain.fillwordgame.databinding.FragmentGameResultBinding

class GameResultFragment : Fragment(R.layout.fragment_game_result) {

    private val binding by viewBinding(FragmentGameResultBinding::bind)

    private val navController by lazy { findNavController() }
    private val args: GameResultFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.titleTextView.text = args.title
        initOnClickListeners()
    }

    private fun initOnClickListeners() = with (binding) {
        exitImageView.setOnClickListener {
            val action = GameResultFragmentDirections.actionGameResultFragmentToMenuFragment()
            navController.navigate(action)
        }
        tryAgainButton.setOnClickListener {
            val action = GameResultFragmentDirections
                .actionGameResultFragmentToDifficultyChooseFragment()
            navController.navigate(action)
        }
    }
}