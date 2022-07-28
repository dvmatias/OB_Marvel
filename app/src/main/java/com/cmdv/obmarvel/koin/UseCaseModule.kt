package com.cmdv.obmarvel.koin

import com.cmdv.domain.usecases.GetCharactersUseCase
import com.cmdv.domain.usecases.GetTotalCharactersUseCase
import com.cmdv.domain.usecases.RemoveStoredCharactersUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetTotalCharactersUseCase(get()) }
    factory { GetCharactersUseCase(get()) }
    factory { RemoveStoredCharactersUseCase(get()) }
}