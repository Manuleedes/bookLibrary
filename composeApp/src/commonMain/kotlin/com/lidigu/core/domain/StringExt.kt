package com.lidigu.core.domain

fun String.toSanitizedPdfFileName(): String {
    return "${this.replace(Regex("[^a-zA-Z0-9.-]"), "_")}.pdf"
}
