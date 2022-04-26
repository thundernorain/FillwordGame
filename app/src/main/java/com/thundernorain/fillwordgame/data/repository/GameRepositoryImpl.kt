package com.thundernorain.fillwordgame.data.repository

import com.thundernorain.fillwordgame.domain.api.GameApi
import com.thundernorain.fillwordgame.domain.model.check_word.CheckWordRequest
import com.thundernorain.fillwordgame.domain.model.check_word.CheckWordResponse
import com.thundernorain.fillwordgame.domain.model.fillword_game.Difficulty
import com.thundernorain.fillwordgame.domain.model.fillword_game.FillwordRequest
import com.thundernorain.fillwordgame.domain.model.fillword_game.FillwordResponse
import com.thundernorain.fillwordgame.domain.repository.GameRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameRepositoryImpl(
    private val api: GameApi,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GameRepository {

    override suspend fun getGameField(
        difficulty: Difficulty
    ): FillwordResponse = withContext(defaultDispatcher) {
        val request = FillwordRequest(difficulty)
        api.getGameField(request)
    }

    override suspend fun checkWord(
        word: String
    ): CheckWordResponse = withContext(defaultDispatcher) {
        val request = CheckWordRequest(word)
        api.checkWord(request)
    }

    override suspend fun closeConnection() = withContext(defaultDispatcher) {
        api.closeConnection()
    }
}