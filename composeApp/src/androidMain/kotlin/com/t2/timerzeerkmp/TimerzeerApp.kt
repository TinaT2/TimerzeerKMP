package com.t2.timerzeerkmp

import android.app.Application
import com.t2.timerzeerkmp.data.di.initKoin

class TimerzeerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppContextHolder.context = this
        initKoin()
    }
}
