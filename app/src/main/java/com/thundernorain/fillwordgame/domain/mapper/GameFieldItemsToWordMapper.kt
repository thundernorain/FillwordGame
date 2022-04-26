package com.thundernorain.fillwordgame.domain.mapper

import com.thundernorain.fillwordgame.domain.model.ui_model.GameFieldItem

interface GameFieldItemsToWordMapper {

    fun map(items: List<GameFieldItem>): String
}