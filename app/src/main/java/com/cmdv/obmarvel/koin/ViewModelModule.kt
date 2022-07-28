package com.cmdv.obmarvel.koin

import com.cmdv.ph_character_details.ui.viewmodels.CharacterDetailsViewModel
import com.cmdv.ph_home.ui.viewmodels.CharactersViewModel
import com.cmdv.ph_home.ui.viewmodels.FavoritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CharactersViewModel(get(), get(), get(), get(), get()) }
    viewModel { FavoritesViewModel(get(), get(), get()) }
    viewModel { CharacterDetailsViewModel(get(), get()) }
}