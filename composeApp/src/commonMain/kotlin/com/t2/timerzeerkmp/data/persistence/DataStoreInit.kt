package com.t2.timerzeerkmp.data.persistence

import com.t2.timerzeerkmp.domain.persistence.SettingsPersistence
import com.t2.timerzeerkmp.domain.persistence.TimerPersistence

expect fun createTimerPersistence(): TimerPersistence
expect fun createSettingsPersistence(): SettingsPersistence