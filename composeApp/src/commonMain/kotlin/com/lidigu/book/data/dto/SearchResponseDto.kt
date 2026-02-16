package com.lidigu.book.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    @SerialName("total") val total: String,
    @SerialName("books") val books: List<SearchedBookDto>
)
