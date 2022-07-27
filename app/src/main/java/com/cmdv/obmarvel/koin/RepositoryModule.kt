package com.cmdv.obmarvel.koin

import com.cmdv.data.repositories.CharactersRepositoryImpl
import com.cmdv.domain.repositories.CharactersRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<CharactersRepository> { CharactersRepositoryImpl(get()) }
}