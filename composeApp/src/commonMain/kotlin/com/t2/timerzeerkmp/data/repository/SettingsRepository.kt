package com.t2.timerzeerkmp.data.repository

import com.t2.timerzeerkmp.domain.persistence.SettingsPersistence

class SettingsRepository(private val persistence: SettingsPersistence) :
    SettingsPersistence by persistence
