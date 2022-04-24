package com.thundernorain.fillwordgame.di

import com.thundernorain.fillwordgame.presentation.mapper.CheckWordResponseMapperImpl
import com.thundernorain.fillwordgame.presentation.mapper.FillwordResponseMapperImpl
import org.koin.dsl.module

val mapperModule = module {
    factory { CheckWordResponseMapperImpl() }
    factory { FillwordResponseMapperImpl() }
}