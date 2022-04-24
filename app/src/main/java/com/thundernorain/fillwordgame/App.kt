package com.thundernorain.fillwordgame

import android.app.Application
import com.thundernorain.fillwordgame.di.mapperModule
import com.thundernorain.fillwordgame.di.networkModule
import com.thundernorain.fillwordgame.di.viewmodelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    private val koinModules = listOf(
        viewmodelModule,
        networkModule,
        mapperModule,
    )

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(koinModules)
        }
    }
}