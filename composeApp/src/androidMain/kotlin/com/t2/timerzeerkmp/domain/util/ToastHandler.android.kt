package com.t2.timerzeerkmp.domain.util

import android.content.Context
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object ToastHandler : KoinComponent {
    actual fun show(message: String) {
        val context: Context by inject()
        android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show()
    }
}