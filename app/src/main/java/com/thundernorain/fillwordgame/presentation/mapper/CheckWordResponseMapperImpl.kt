package com.thundernorain.fillwordgame.presentation.mapper

import com.thundernorain.fillwordgame.domain.mapper.CheckWordResponseMapper
import com.thundernorain.fillwordgame.domain.model.check_word.CheckWordResponse
import com.thundernorain.fillwordgame.domain.model.check_word.CheckWordResult
import com.thundernorain.fillwordgame.domain.model.ui_model.CheckWordResponseUiModel
import com.thundernorain.fillwordgame.domain.model.ui_model.CheckWordResultUiModel

class CheckWordResponseMapperImpl : CheckWordResponseMapper {

    override fun map(response: CheckWordResponse): CheckWordResponseUiModel {
        return CheckWordResponseUiModel(
            result = mapResult(response.result)
        )
    }

    private fun mapResult(
        result: CheckWordResult?
    ) = when (result) {
        CheckWordResult.CORRECT -> CheckWordResultUiModel.CORRECT
        CheckWordResult.WRONG -> CheckWordResultUiModel.WRONG
        null -> CheckWordResultUiModel.SERVER_ERROR
    }
}