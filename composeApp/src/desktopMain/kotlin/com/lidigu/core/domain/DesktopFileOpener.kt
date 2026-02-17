package com.lidigu.core.domain

import java.awt.Desktop
import java.io.File

class DesktopFileOpener: FileOpener {
    override fun openFile(path: String) {
        val file = File(path)
        if (!file.exists()) {
            println("File does not exist: $path")
            return
        }

        val osName = System.getProperty("os.name").lowercase()
        try {
            if (osName.contains("linux")) {
                Runtime.getRuntime().exec(arrayOf("xdg-open", path))
            } else if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file)
            } else {
                println("Desktop is not supported on this platform.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Fallback to AWT Desktop if xdg-open fails or for other OSs
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(file)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }
}
