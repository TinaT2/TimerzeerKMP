package com.t2.timerzeerkmp.data.persistence

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import android.content.Context
import com.t2.timerzeerkmp.domain.persistence.TimerPersistence
import org.koin.mp.KoinPlatform.getKoin

actual fun createTimerPersistence(): TimerPersistence {
    val context: Context = getKoin().get() // pull android Context from DI
    val store = PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile("timer_prefs.preferences_pb")
    }
    return DataStoreTimerPersistence(store)
}
