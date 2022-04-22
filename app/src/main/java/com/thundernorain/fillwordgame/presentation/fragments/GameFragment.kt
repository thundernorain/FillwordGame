package com.thundernorain.fillwordgame.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thundernorain.fillwordgame.R
import com.thundernorain.fillwordgame.databinding.FragmentGameBinding
import com.thundernorain.fillwordgame.presentation.extensions.collectLatestOn
import com.thundernorain.fillwordgame.presentation.viewmodel.GameViewModel

class GameFragment : Fragment(R.layout.fragment_game) {

    private val viewModel by viewModels<GameViewModel>()
    private val binding by viewBinding(FragmentGameBinding::bind)

    private val navController by lazy { findNavController() }
    private val args: GameFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewCreated(args.difficulty)
        observeData()
    }

    private fun observeData() = with (viewModel) {
        remainingTime.collectLatestOn(viewLifecycleOwner) {  }
        correctWordsCount.collectLatestOn(viewLifecycleOwner) {  }
    }
}