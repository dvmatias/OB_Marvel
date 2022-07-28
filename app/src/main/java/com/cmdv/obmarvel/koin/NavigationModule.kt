package com.cmdv.obmarvel.koin

import com.cmdv.core.navigator.Navigator
import com.cmdv.obmarvel.navigator.NavigatorImpl
import org.koin.dsl.module

val navigationModule = module {
    single<Navigator> { NavigatorImpl() }
}