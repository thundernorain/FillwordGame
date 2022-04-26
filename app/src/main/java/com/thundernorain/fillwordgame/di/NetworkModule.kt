package com.thundernorain.fillwordgame.di

import com.thundernorain.fillwordgame.data.api.GameApiImpl
import com.thundernorain.fillwordgame.data.repository.GameRepositoryImpl
import com.thundernorain.fillwordgame.domain.api.GameApi
import com.thundernorain.fillwordgame.domain.interactor.CheckWordInteractor
import com.thundernorain.fillwordgame.domain.interactor.CloseConnectionInteractor
import com.thundernorain.fillwordgame.domain.interactor.GameFieldInteractor
import com.thundernorain.fillwordgame.domain.repository.GameRepository
import org.koin.dsl.module

val networkModule = module {
    // Api
    factory<GameApi> { GameApiImpl() }

    // Repositories
    single<GameRepository> { GameRepositoryImpl(get()) }

    // Interactors
    factory { CheckWordInteractor(get()) }
    factory { GameFieldInteractor(get()) }
    factory { CloseConnectionInteractor(get()) }
}