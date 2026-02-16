package com.lidigu.book.data.network

import com.lidigu.book.data.dto.BookDetailDto
import com.lidigu.book.data.dto.SearchResponseDto
import com.lidigu.book.domain.Book
import com.lidigu.core.data.safeCall
import com.lidigu.core.domain.DataError
import com.lidigu.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://api.itbook.store/1.0"

class KtorRemoteBookDataSource(
    private val httpClient: HttpClient
): RemoteBookDataSource {

    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall<SearchResponseDto> {
            httpClient.get(
                urlString = "$BASE_URL/search/$query"
            )
        }
    }

    override suspend fun getBookDetails(bookWorkId: String): Result<BookDetailDto, DataError.Remote> {
        return safeCall<BookDetailDto> {
            httpClient.get(
                urlString = "$BASE_URL/books/$bookWorkId"
            )
        }
    }
}