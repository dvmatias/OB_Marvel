package com.cmdv.obmarvel.koin

import com.cmdv.data.errorhandling.CharacterDetailsApiErrorHandler
import com.cmdv.data.errorhandling.CharactersApiErrorHandler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val errorHandlerModule = module {
    single { CharactersApiErrorHandler(androidContext()) }
    single { CharacterDetailsApiErrorHandler(androidContext()) }
}