package com.thundernorain.fillwordgame.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thundernorain.fillwordgame.R
import com.thundernorain.fillwordgame.databinding.FragmentMenuBinding

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val binding by viewBinding(FragmentMenuBinding::bind)
    private val navController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() = with(binding) {
        playMenuButton.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToDifficultyChooseFragment()
            navController.navigate(action)
        }
        aboutMenuButton.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToAboutFragment()
            navController.navigate(action)
        }
        exitMenuButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}