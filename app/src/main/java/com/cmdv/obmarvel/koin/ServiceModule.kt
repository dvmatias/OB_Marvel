package com.cmdv.obmarvel.koin

import com.cmdv.data.sources.apiservices.CharactersApi
import com.cmdv.data.sources.apiservices.CharactersService
import com.cmdv.obmarvel.RETROFIT_INSTANCE_NAME
import org.koin.core.qualifier.named
import org.koin.dsl.module

val serviceModule = module {
    single<CharactersApi> { CharactersService(get(named(RETROFIT_INSTANCE_NAME))) }
}