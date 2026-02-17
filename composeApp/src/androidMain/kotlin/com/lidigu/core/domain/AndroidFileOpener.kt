package com.lidigu.core.domain

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import android.widget.Toast
import java.io.File

class AndroidFileOpener(
    private val context: Context
): FileOpener {
    override fun openFile(path: String) {
        val file = File(path)
        if (!file.exists()) {
            Toast.makeText(context, "File not found", Toast.LENGTH_SHORT).show()
            return
        }

        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        
        val chooserIntent = Intent.createChooser(intent, "Open PDF with...")
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        chooserIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        
        try {
            context.startActivity(chooserIntent)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error opening PDF: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
