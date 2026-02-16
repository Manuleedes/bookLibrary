package com.lidigu.book.presentation.book_detail

import com.lidigu.book.domain.Book

data class BookDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val book: Book? = null
)
