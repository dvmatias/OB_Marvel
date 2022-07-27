package com.cmdv.obmarvel.koin

import com.cmdv.data.networking.NetworkHandler
import com.cmdv.data.networking.ApiHandler
import com.cmdv.obmarvel.RETROFIT_INSTANCE_NAME
import com.cmdv.obmarvel.retrofit.RetrofitManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {
    single { NetworkHandler(androidContext()) }
    factory { ApiHandler(get()) }
    single(named(RETROFIT_INSTANCE_NAME)) { RetrofitManager().getInstance() }
}