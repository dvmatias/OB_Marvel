package com.cmdv.obmarvel.koin

import com.cmdv.data.repositories.CharacterDetailsRepositoryImpl
import com.cmdv.data.repositories.CharactersRepositoryImpl
import com.cmdv.data.repositories.FavoriteCharacterRepositoryImpl
import com.cmdv.domain.repositories.CharacterDetailsRepository
import com.cmdv.domain.repositories.CharactersRepository
import com.cmdv.domain.repositories.FavoriteCharacterRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<CharactersRepository> { CharactersRepositoryImpl(get(), get(), get()) }
    factory<FavoriteCharacterRepository> { FavoriteCharacterRepositoryImpl(get(), get()) }
    factory<CharacterDetailsRepository> { CharacterDetailsRepositoryImpl(get(), get()) }
}