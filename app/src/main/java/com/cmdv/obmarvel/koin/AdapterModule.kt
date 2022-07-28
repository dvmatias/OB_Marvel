package com.cmdv.obmarvel.koin

import com.cmdv.ph_home.ui.adapters.CharacterAdapter
import com.cmdv.ph_home.ui.adapters.IndexFavoriteCharacterAdapter
import org.koin.dsl.module

val adapterModule = module {
    factory { CharacterAdapter() }
    factory { IndexFavoriteCharacterAdapter() }
}