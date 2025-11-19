package com.t2.timerzeerkmp.data.di

import com.t2.timerzeerkmp.data.database.getDatabaseBuilder
import org.koin.dsl.module

val androidModule = module {
    single { getDatabaseBuilder(get()) }
}