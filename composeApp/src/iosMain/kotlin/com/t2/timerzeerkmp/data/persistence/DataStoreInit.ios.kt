package com.t2.timerzeerkmp.data.persistence

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.t2.timerzeerkmp.domain.persistence.TimerPersistence
import okio.Path.Companion.toPath
import platform.Foundation.NSHomeDirectory

actual fun createTimerPersistence(): TimerPersistence {
    val store = PreferenceDataStoreFactory.createWithPath {
        val path = NSHomeDirectory() + "/timer_prefs.preferences_pb"
        path.toPath()
    }
    return DataStoreTimerPersistence(store)
}