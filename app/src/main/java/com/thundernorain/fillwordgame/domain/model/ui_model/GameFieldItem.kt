package com.thundernorain.fillwordgame.domain.model.ui_model

data class GameFieldItem(
    var position: Int = 0,
    val letter: Char,
    var isSelected: Boolean = false,
    var isCorrect: Boolean = false,
)
