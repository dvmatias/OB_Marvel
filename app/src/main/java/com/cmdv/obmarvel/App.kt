package com.cmdv.obmarvel

import android.app.Application
import com.cmdv.obmarvel.koin.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Application class. Main app entry point.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    /**
     * Init Koin in this application as a standalone instance.
     */
    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    adapterModule,
                    navigationModule,
                    networkModule,
                    repositoryModule,
                    roomModule,
                    serviceModule,
                    useCaseModule,
                    viewModelModule,
                    errorHandlerModule
                )
            )
        }
    }
}