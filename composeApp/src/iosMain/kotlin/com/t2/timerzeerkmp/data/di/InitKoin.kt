package com.t2.timerzeerkmp.data.di

fun startKoinForiOS() {
    initKoin {
        modules(iosModule)
    }
}