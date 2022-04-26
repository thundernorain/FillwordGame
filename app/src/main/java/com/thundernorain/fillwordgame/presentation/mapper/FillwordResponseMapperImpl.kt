package com.thundernorain.fillwordgame.presentation.mapper

import com.thundernorain.fillwordgame.domain.mapper.FillwordResponseMapper
import com.thundernorain.fillwordgame.domain.model.fillword_game.FillwordResponse
import com.thundernorain.fillwordgame.domain.model.ui_model.FillwordResponseUiModel
import com.thundernorain.fillwordgame.domain.model.ui_model.GameFieldItem

class FillwordResponseMapperImpl : FillwordResponseMapper {

    override fun map(response: FillwordResponse): FillwordResponseUiModel {
        return FillwordResponseUiModel(
            gameField = mapGameField(response.gameField),
            wordsCount = response.wordsCount ?: 0,
            timeToSolve = response.timeToSolve ?: 0L
        )
    }

    private fun mapGameField(
        field: List<List<Char>>?
    ) : ArrayList<GameFieldItem> {
        val result = ArrayList<GameFieldItem>()
        field?.let {
            for (item in field)
                result.addAll(mapGameFieldItems(item))
        }
        return result
    }

    private fun mapGameFieldItems(items: List<Char>) = items.map { char ->
        GameFieldItem(letter = char)
    }
}