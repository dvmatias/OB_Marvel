package com.cmdv.obmarvel.koin

import com.cmdv.ph_home.ui.adapters.CharacterAdapter
import org.koin.dsl.module

val adapterModule = module {
    factory { CharacterAdapter() }
}