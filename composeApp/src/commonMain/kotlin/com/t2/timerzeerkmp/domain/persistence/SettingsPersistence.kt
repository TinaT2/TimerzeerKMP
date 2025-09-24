package com.t2.timerzeerkmp.domain.persistence

import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.StringResource

interface SettingsPersistence {
    suspend fun saveEndingAnimation(nameId: StringResource)
    suspend fun saveBackgroundTheme(nameId: StringResource)
    suspend fun saveFontStyle(nameId: StringResource)

    suspend fun getEndingAnimation(): Flow<String?>
    suspend fun getBackgroundTheme(): Flow<String?>
    suspend fun getFontStyleKeResource(): Flow<String?>
}