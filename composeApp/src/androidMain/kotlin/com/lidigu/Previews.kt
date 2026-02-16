package com.lidigu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.lidigu.book.domain.Book
import com.lidigu.book.presentation.book_list.BookListScreen
import com.lidigu.book.presentation.book_list.BookListState
import com.lidigu.book.presentation.book_list.components.BookSearchBar

@Preview
@Composable
private fun BookSearchBarPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        BookSearchBar(
            searchQuery = "",
            onSearchQueryChange = {},
            onImeSearch = {},
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

private val books = (1..100).map {
    Book(
        id = it.toString(),
        title = "Book $it",
        subtitle = "Subtitle $it",
        authors = "Philipp Lackner",
        publisher = "Publisher $it",
        pages = "100",
        year = "2024",
        rating = 4.67854,
        description = "Description $it",
        price = "$10.00",
        imageUrl = "https://test.com",
        url = "https://test.com",
        downloadUrl = null
    )
}

@Preview
@Composable
private fun BookListScreenPreview() {
    BookListScreen(
        state = BookListState(
            searchResults = books
        ),
        onAction = {}
    )
}