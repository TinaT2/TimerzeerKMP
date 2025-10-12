@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.t2.timerzeerkmp.domain.util

expect object ShareExtension{
    fun share(shareText: String)
}