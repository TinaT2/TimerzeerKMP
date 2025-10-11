@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.t2.timerzeerkmp.domain.util
// iosMain
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGFloat
import platform.CoreGraphics.CGRect
import platform.CoreGraphics.CGRectGetHeight
import platform.CoreGraphics.CGRectGetWidth
import platform.CoreGraphics.CGRectMake
import platform.UIKit.NSTextAlignmentCenter
import platform.UIKit.UIApplication
import platform.UIKit.UIColor
import platform.UIKit.UIFont
import platform.UIKit.UILabel
import platform.UIKit.UIView

actual object ToastHandler {

    @OptIn(ExperimentalForeignApi::class)
    actual fun show(message: String) {
        val window = UIApplication.sharedApplication.keyWindow ?: return

        val bounds: CValue<CGRect> = window.bounds
        val screenWidth: CGFloat = CGRectGetWidth(bounds)
        val screenHeight: CGFloat = CGRectGetHeight(bounds)

        val width = screenWidth - 40.0
        val height = 50.0
        val x = 20.0
        val y = screenHeight - 100.0

        val toast = UILabel(CGRectMake(x, y, width, height)).apply {
            text = message
            textColor = UIColor.whiteColor
            textAlignment = NSTextAlignmentCenter
            font = UIFont.systemFontOfSize(14.0)
            backgroundColor = UIColor.blackColor.colorWithAlphaComponent(0.7)
            alpha = 0.0
            layer.cornerRadius = 10.0
            clipsToBounds = true
        }

        window.addSubview(toast)

        UIView.animateWithDuration(
            duration = 0.5,
            animations = {
                toast.alpha = 1.0
            },
            completion = {
                UIView.animateWithDuration(
                    duration = 0.5,
                    delay = 2.0,
                    options = 0.toULong(),
                    animations = {
                        toast.alpha = 0.0
                    },
                    completion = {
                        toast.removeFromSuperview()
                    }
                )
            }
        )
    }
}