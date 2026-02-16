package com.lidigu

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.lidigu.app.App
import com.lidigu.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "CMP-Bookpedia",
        ) {
            App()
        }
    }
}