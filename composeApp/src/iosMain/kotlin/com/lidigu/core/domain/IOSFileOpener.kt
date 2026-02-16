package com.lidigu.core.domain

import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIDocumentInteractionController

class IOSFileOpener : FileOpener {
    override fun openFile(path: String) {
        val url = NSURL.fileURLWithPath(path)
        val controller = UIDocumentInteractionController.interactionControllerWithURL(url)
        val window = UIApplication.sharedApplication.keyWindow
        val rootViewController = window?.rootViewController
        if (rootViewController != null) {
            controller.presentPreviewAnimated(true)
        }
    }
}
