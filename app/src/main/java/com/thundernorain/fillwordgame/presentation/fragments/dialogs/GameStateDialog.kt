package com.thundernorain.fillwordgame.presentation.fragments.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.thundernorain.fillwordgame.R

class GameStateDialog : DialogFragment() {

    private val title: String by lazy {
        requireNotNull(arguments?.getString(TITLE))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(title)
            .setPositiveButton(getString(R.string.dialog_answer_ok)) { _,_ -> }
            .create()
    }

    companion object {
        const val TAG = "GameStateDialog"

        private const val TITLE = "titleRes"

        fun getInstance(title: String) = GameStateDialog().apply {
            arguments = bundleOf(TITLE to title)
        }
    }
}