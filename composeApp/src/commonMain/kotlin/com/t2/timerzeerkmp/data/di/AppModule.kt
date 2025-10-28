package com.t2.timerzeerkmp.data.di

import com.t2.timerzeerkmp.data.database.TimerDatabase
import com.t2.timerzeerkmp.data.database.createTimerDatabase
import com.t2.timerzeerkmp.data.persistence.createSettingsPersistence
import com.t2.timerzeerkmp.data.persistence.createTimerPersistence
import com.t2.timerzeerkmp.data.repository.SettingsRepository
import com.t2.timerzeerkmp.data.repository.TimerRepository
import com.t2.timerzeerkmp.domain.getTimerController
import com.t2.timerzeerkmp.domain.persistence.SettingsPersistence
import com.t2.timerzeerkmp.domain.persistence.TimerPersistence
import com.t2.timerzeerkmp.presentation.fullScreenTimer.FullScreenTimerViewModel
import com.t2.timerzeerkmp.presentation.timerPreview.TimerPreviewViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val coreModule = module {
    single<TimerPersistence> { createTimerPersistence() }
    single<SettingsPersistence> { createSettingsPersistence() }
    viewModelOf(::TimerPreviewViewModel)
    viewModelOf(::FullScreenTimerViewModel)
    singleOf(::SettingsRepository)
    singleOf(::TimerRepository)
    singleOf(::getTimerController)
    single { createTimerDatabase() }
    single { get<TimerDatabase>().timerDao() }
}
