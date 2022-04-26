package com.thundernorain.fillwordgame.domain.interactor

import com.thundernorain.fillwordgame.domain.repository.GameRepository

class CloseConnectionInteractor(
    private val repository: GameRepository
) {

    suspend fun invoke() = repository.closeConnection()
}