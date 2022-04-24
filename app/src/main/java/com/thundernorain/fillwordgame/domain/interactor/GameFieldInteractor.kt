package com.thundernorain.fillwordgame.domain.interactor

import com.thundernorain.fillwordgame.domain.model.fillword_game.Difficulty
import com.thundernorain.fillwordgame.domain.repository.GameRepository

class GameFieldInteractor(
    private val repository: GameRepository
) {

    suspend fun invoke(difficulty: Difficulty) = repository.getGameField(difficulty)
}