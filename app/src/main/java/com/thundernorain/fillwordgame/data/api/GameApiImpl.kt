package com.thundernorain.fillwordgame.data.api

import com.thundernorain.fillwordgame.domain.api.GameApi
import com.thundernorain.fillwordgame.domain.model.check_word.CheckWordRequest
import com.thundernorain.fillwordgame.domain.model.check_word.CheckWordResponse
import com.thundernorain.fillwordgame.domain.model.fillword_game.FillwordRequest
import com.thundernorain.fillwordgame.domain.model.fillword_game.FillwordResponse

class GameApiImpl() : GameApi {

    override fun getGameField(request: FillwordRequest): FillwordResponse {
        TODO("Not yet implemented")
    }

    override fun checkWord(request: CheckWordRequest): CheckWordResponse {
        TODO("Not yet implemented")
    }
}