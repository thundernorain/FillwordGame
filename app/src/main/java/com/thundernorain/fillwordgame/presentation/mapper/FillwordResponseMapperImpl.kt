package com.thundernorain.fillwordgame.presentation.mapper

import com.thundernorain.fillwordgame.domain.mapper.FillwordResponseMapper
import com.thundernorain.fillwordgame.domain.model.fillword_game.FillwordResponse
import com.thundernorain.fillwordgame.domain.model.ui_model.FillwordResponseUiModel

class FillwordResponseMapperImpl : FillwordResponseMapper {

    override fun map(response: FillwordResponse): FillwordResponseUiModel {
        return FillwordResponseUiModel(
            gameField = mapGameField(response.gameField),
            wordsCount = response.wordsCount ?: 0,
            timeToSolve = response.timeToSolve ?: 0L
        )
    }

    fun mapGameField(
        field: List<List<Char>>?
    ) = ArrayList(field?.map { ArrayList(it) }.orEmpty())
}