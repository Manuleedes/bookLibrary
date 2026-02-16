package com.lidigu.core.domain

import java.awt.Desktop
import java.io.File

class DesktopFileOpener: FileOpener {
    override fun openFile(path: String) {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(File(path))
        }
    }
}
