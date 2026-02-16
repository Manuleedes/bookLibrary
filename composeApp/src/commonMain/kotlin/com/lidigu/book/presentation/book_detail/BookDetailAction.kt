package com.lidigu.book.presentation.book_detail

import com.lidigu.book.domain.Book

sealed interface BookDetailAction {
    data object OnBackClick: BookDetailAction

    data class OnSelectedBookChange(val book: Book): BookDetailAction
    data object OnDownloadClick: BookDetailAction
    data object OnReadClick: BookDetailAction
}