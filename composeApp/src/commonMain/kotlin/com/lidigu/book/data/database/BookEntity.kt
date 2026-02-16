package com.lidigu.book.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val title: String,
    val subtitle: String,
    val authors: String,
    val publisher: String,
    val pages: String,
    val year: String,
    val rating: Double,
    val description: String,
    val price: String,
    val imageUrl: String,
    val url: String,
    val downloadUrl: String?,
    val localPath: String? = null
)
