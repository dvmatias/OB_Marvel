package com.cmdv.obmarvel.koin

import com.cmdv.domain.usecases.GetCharactersUseCase
import com.cmdv.domain.usecases.GetTotalCharactersUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetTotalCharactersUseCase(get()) }
    factory { GetCharactersUseCase(get()) }
}