package com.lidigu

import androidx.compose.ui.window.ComposeUIViewController
import com.lidigu.app.App
import com.lidigu.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }