package com.thundernorain.fillwordgame.domain.repository

import com.thundernorain.fillwordgame.domain.model.check_word.CheckWordResponse
import com.thundernorain.fillwordgame.domain.model.fillword_game.Difficulty
import com.thundernorain.fillwordgame.domain.model.fillword_game.FillwordResponse

interface GameRepository {

    suspend fun getGameField(difficulty: Difficulty): FillwordResponse

    suspend fun checkWord(word: String): CheckWordResponse

    suspend fun closeConnection()
}