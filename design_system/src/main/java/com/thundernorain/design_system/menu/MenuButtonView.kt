package com.thundernorain.design_system.menu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.thundernorain.design_system.R
import com.thundernorain.design_system.databinding.ViewMenuButtonBinding

class MenuButtonView(
    context: Context,
    attrs: AttributeSet
) : ConstraintLayout(context, attrs) {

    var text: String = ""
        set(value) {
            field = value
            onTextChanged()
        }

    var onButtonClickListener: (() -> Unit)? = null
        set(value) {
            field = value
            onButtonClickListenerChanged()
        }

    private val binding = ViewMenuButtonBinding
        .inflate(LayoutInflater.from(context), this, true)

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MenuButtonView,
            0, 0
        ).apply {

            try {
                text = getString(R.styleable.MenuButtonView_text).orEmpty()
            } finally {
                recycle()
            }
        }
    }

    private fun onTextChanged() {
        binding.buttonLabelTextView.text = text
    }

    private fun onButtonClickListenerChanged() {
        binding.buttonBackgroundImageView.setOnClickListener {
            onButtonClickListener?.invoke()
        }
    }
}