package com.lidigu.book.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchedBookDto(
    @SerialName("isbn13") val id: String,
    @SerialName("title") val title: String,
    @SerialName("subtitle") val subtitle: String,
    @SerialName("price") val price: String,
    @SerialName("image") val image: String,
    @SerialName("url") val url: String
)
