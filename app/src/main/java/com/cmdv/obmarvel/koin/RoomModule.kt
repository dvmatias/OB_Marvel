package com.cmdv.obmarvel.koin

import com.cmdv.data.database.CharacterRoomDatabase
import com.cmdv.data.database.FavoriteCharacterRoomDataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomModule = module {
    single { CharacterRoomDatabase.getInstance(androidApplication().applicationContext).characterDao }
    single { FavoriteCharacterRoomDataBase.getInstance(androidApplication().applicationContext).favoriteCharacterDao }
}