package com.thundernorain.fillwordgame.di

import com.thundernorain.fillwordgame.presentation.viewmodel.GameViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewmodelModule = module {
    viewModel { GameViewModel(get(), get(), get(), get(), get(), get(), get()) }
}