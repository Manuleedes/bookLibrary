package com.lidigu.book.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDetailDto(
    @SerialName("title") val title: String,
    @SerialName("subtitle") val subtitle: String,
    @SerialName("authors") val authors: String,
    @SerialName("publisher") val publisher: String,
    @SerialName("isbn13") val isbn13: String,
    @SerialName("pages") val pages: String,
    @SerialName("year") val year: String,
    @SerialName("rating") val rating: String,
    @SerialName("desc") val desc: String,
    @SerialName("price") val price: String,
    @SerialName("image") val image: String,
    @SerialName("url") val url: String,
    @SerialName("pdf") val pdf: Map<String, String>? = null
)
