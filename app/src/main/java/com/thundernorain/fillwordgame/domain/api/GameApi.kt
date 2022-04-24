package com.thundernorain.fillwordgame.domain.api

import com.thundernorain.fillwordgame.domain.model.check_word.CheckWordRequest
import com.thundernorain.fillwordgame.domain.model.check_word.CheckWordResponse
import com.thundernorain.fillwordgame.domain.model.fillword_game.FillwordRequest
import com.thundernorain.fillwordgame.domain.model.fillword_game.FillwordResponse

interface GameApi {

    fun getGameField(request: FillwordRequest): FillwordResponse

    fun checkWord(request: CheckWordRequest): CheckWordResponse
}