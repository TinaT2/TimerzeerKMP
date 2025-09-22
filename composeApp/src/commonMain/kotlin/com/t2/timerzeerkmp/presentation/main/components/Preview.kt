package com.t2.timerzeerkmp.presentation.main.components

import androidx.compose.runtime.Composable
import com.t2.timerzeerkmp.presentation.main.theme.TimerzeerTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ThemedPreview(content: @Composable () -> Unit) {
    TimerzeerTheme {
        content()
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true,
    backgroundColor = 0xFFFAFCFC
)
annotation class LightDarkPreviews
