package com.thundernorain.fillwordgame.domain.mapper

import com.thundernorain.fillwordgame.domain.model.fillword_game.FillwordResponse
import com.thundernorain.fillwordgame.domain.model.ui_model.FillwordResponseUiModel

interface FillwordResponseMapper {

    fun map(response: FillwordResponse): FillwordResponseUiModel
}