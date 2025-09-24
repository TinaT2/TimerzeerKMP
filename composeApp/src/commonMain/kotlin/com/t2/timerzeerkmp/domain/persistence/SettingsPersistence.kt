package com.t2.timerzeerkmp.domain.persistence

import org.jetbrains.compose.resources.StringResource

interface SettingsPersistence {
    suspend fun saveEndingAnimation(nameId: StringResource)
    suspend fun saveBackgroundTheme(nameId: StringResource)
    suspend fun saveFontStyle(nameId: StringResource)

    suspend fun getEndingAnimation(): String?
    suspend fun getBackgroundTheme(): String?
    suspend fun getFontStyleKeResource(): String?
}