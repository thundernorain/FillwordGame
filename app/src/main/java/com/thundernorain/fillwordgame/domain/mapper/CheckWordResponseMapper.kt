package com.thundernorain.fillwordgame.domain.mapper

import com.thundernorain.fillwordgame.domain.model.check_word.CheckWordResponse
import com.thundernorain.fillwordgame.domain.model.ui_model.CheckWordResponseUiModel

interface CheckWordResponseMapper {

    fun map(response: CheckWordResponse): CheckWordResponseUiModel
}