package com.lidigu.book.data.mappers

import com.lidigu.book.data.database.BookEntity
import com.lidigu.book.data.dto.BookDetailDto
import com.lidigu.book.data.dto.SearchedBookDto
import com.lidigu.book.domain.Book

fun SearchedBookDto.toBook(): Book {
    return Book(
        id = id,
        title = title,
        subtitle = subtitle,
        authors = "Unknown",
        publisher = "Unknown",
        pages = "Unknown",
        year = "Unknown",
        rating = 0.0,
        description = "Unknown",
        price = price,
        imageUrl = image,
        url = url,
        downloadUrl = null,
        localPath = null
    )
}

fun Book.toBookEntity(): BookEntity {
    return BookEntity(
        id = id,
        title = title,
        subtitle = subtitle,
        authors = authors,
        publisher = publisher,
        pages = pages,
        year = year,
        rating = rating,
        description = description,
        price = price,
        imageUrl = imageUrl,
        url = url,
        downloadUrl = downloadUrl,
        localPath = localPath
    )
}

fun BookEntity.toBook(): Book {
    return Book(
        id = id,
        title = title,
        subtitle = subtitle,
        authors = authors,
        publisher = publisher,
        pages = pages,
        year = year,
        rating = rating,
        description = description,
        price = price,
        imageUrl = imageUrl,
        url = url,
        downloadUrl = downloadUrl,
        localPath = localPath
    )
}
fun BookDetailDto.toBook(): Book {
    return Book(
        id = isbn13,
        title = title,
        subtitle = subtitle,
        authors = authors,
        publisher = publisher,
        pages = pages,
        year = year,
        rating = rating.toDoubleOrNull() ?: 0.0,
        description = desc,
        price = price,
        imageUrl = image,
        url = url,
        downloadUrl = pdf?.values?.firstOrNull(),
        localPath = null
    )
}
