package com.lidigu.book.presentation.book_detail

import com.lidigu.book.domain.Book

data class BookDetailState(
    val isLoading: Boolean = true,

    val isDownloading: Boolean = false,
    val downloadProgress: Float = 0f,
    val isDownloaded: Boolean = false,
    val book: Book? = null
)
