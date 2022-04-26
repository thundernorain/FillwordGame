package com.thundernorain.fillwordgame.presentation.mapper

import com.thundernorain.fillwordgame.domain.mapper.GameFieldItemsToWordMapper
import com.thundernorain.fillwordgame.domain.model.ui_model.GameFieldItem
import java.lang.StringBuilder

class GameFieldItemsToWordMapperImpl : GameFieldItemsToWordMapper {

    override fun map(items: List<GameFieldItem>): String {
        val result = StringBuilder()
        for (item in items) {
            result.append(item.letter)
        }
        return result.toString()
    }
}