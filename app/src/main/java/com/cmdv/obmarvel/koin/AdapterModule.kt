package com.cmdv.obmarvel.koin

import com.cmdv.ph_character_details.ui.adapters.ComicAdapter
import com.cmdv.ph_character_details.ui.adapters.SerieAdapter
import com.cmdv.ph_home.ui.adapters.CharacterAdapter
import com.cmdv.ph_home.ui.adapters.IndexFavoriteCharacterAdapter
import org.koin.dsl.module

val adapterModule = module {
    factory { CharacterAdapter() }
    factory { IndexFavoriteCharacterAdapter() }
    factory { ComicAdapter() }
    factory { SerieAdapter() }
}