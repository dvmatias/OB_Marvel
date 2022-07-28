package com.cmdv.obmarvel.koin

import com.cmdv.data.database.CharactersRoomDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomModule = module {
    single { CharactersRoomDatabase.getInstance(androidApplication().applicationContext).charactersDao }
}