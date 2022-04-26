package com.thundernorain.fillwordgame.di

import com.thundernorain.fillwordgame.domain.mapper.CheckWordResponseMapper
import com.thundernorain.fillwordgame.domain.mapper.FillwordResponseMapper
import com.thundernorain.fillwordgame.domain.mapper.GameFieldItemsToWordMapper
import com.thundernorain.fillwordgame.domain.mapper.TimeLongMapper
import com.thundernorain.fillwordgame.presentation.mapper.CheckWordResponseMapperImpl
import com.thundernorain.fillwordgame.presentation.mapper.FillwordResponseMapperImpl
import com.thundernorain.fillwordgame.presentation.mapper.GameFieldItemsToWordMapperImpl
import com.thundernorain.fillwordgame.presentation.mapper.TimeLongMapperImpl
import org.koin.dsl.module

val mapperModule = module {
    factory<CheckWordResponseMapper> { CheckWordResponseMapperImpl() }
    factory<FillwordResponseMapper> { FillwordResponseMapperImpl() }
    factory<TimeLongMapper> { TimeLongMapperImpl() }
    factory<GameFieldItemsToWordMapper> { GameFieldItemsToWordMapperImpl() }
}