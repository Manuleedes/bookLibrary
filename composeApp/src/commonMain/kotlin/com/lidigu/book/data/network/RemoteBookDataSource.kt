package com.lidigu.book.data.network

import com.lidigu.book.data.dto.BookWorkDto
import com.lidigu.book.data.dto.SearchResponseDto
import com.lidigu.core.domain.DataError
import com.lidigu.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getBookDetails(bookWorkId: String): Result<BookWorkDto, DataError.Remote>
}