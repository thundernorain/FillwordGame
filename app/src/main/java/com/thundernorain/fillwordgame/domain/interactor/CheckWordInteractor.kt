package com.thundernorain.fillwordgame.domain.interactor

import com.thundernorain.fillwordgame.domain.repository.GameRepository

class CheckWordInteractor(
    private val repository: GameRepository
) {

    suspend fun invoke(word: String) = repository.checkWord(word)
}