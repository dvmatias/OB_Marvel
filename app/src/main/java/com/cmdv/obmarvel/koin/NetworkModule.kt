package com.cmdv.obmarvel.koin

import com.cmdv.data.networking.NetworkHandler
import com.cmdv.data.networking.ApiHandler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkModule = module {
    single { NetworkHandler(androidContext()) }
    factory { ApiHandler(get()) }
}