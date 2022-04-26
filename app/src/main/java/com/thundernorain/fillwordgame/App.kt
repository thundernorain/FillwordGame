package com.thundernorain.fillwordgame

import android.app.Application
import com.thundernorain.fillwordgame.di.mapperModule
import com.thundernorain.fillwordgame.di.networkModule
import com.thundernorain.fillwordgame.di.viewmodelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    private val koinModules = listOf(
        networkModule,
        mapperModule,
        viewmodelModule,
    )

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            modules(koinModules)
        }
    }
}