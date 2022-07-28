package com.cmdv.obmarvel.koin

import com.cmdv.domain.usecases.*
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetTotalCharactersUseCase(get()) }
    factory { GetCharactersUseCase(get()) }
    factory { RemoveStoredCharactersUseCase(get()) }
    factory { GetFavoriteCharactersUseCase() }
    factory { RemoveAllFavoriteCharacterUseCase() }
    factory { RemoveFavoriteCharacterUseCase() }
}