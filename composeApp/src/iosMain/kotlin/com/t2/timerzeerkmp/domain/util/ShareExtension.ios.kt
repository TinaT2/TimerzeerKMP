@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.t2.timerzeerkmp.domain.util

import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

actual object ShareExtension {
    actual fun share(shareText: String) {
        val activityVC = UIActivityViewController(
        activityItems = listOf(shareText),
            applicationActivities = null
        )

        val rootVC = UIApplication.sharedApplication.keyWindow?.rootViewController
            ?: return

        rootVC.presentViewController(activityVC, animated = true, completion = null)
    }
}