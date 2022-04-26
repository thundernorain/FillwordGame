package com.thundernorain.fillwordgame.domain.api

import com.thundernorain.fillwordgame.domain.model.check_word.CheckWordRequest
import com.thundernorain.fillwordgame.domain.model.check_word.CheckWordResponse
import com.thundernorain.fillwordgame.domain.model.fillword_game.FillwordRequest
import com.thundernorain.fillwordgame.domain.model.fillword_game.FillwordResponse

interface GameApi {

    suspend fun getGameField(request: FillwordRequest): FillwordResponse

    suspend fun checkWord(request: CheckWordRequest): CheckWordResponse

    suspend fun closeConnection()
}