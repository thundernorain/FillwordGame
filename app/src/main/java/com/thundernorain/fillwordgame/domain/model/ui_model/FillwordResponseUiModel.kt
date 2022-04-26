package com.thundernorain.fillwordgame.domain.model.ui_model

data class FillwordResponseUiModel(
    val gameField: ArrayList<GameFieldItem>,
    val wordsCount: Int,
    val timeToSolve: Long,
)
