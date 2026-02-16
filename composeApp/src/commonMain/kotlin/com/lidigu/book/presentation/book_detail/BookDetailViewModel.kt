package com.lidigu.book.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.lidigu.app.Route
import com.lidigu.book.domain.BookRepository
import com.lidigu.book.domain.DownloadManager
import com.lidigu.book.domain.DownloadState
import com.lidigu.core.domain.FileOpener
import com.lidigu.core.domain.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val bookRepository: BookRepository,
    private val downloadManager: DownloadManager,
    private val fileOpener: FileOpener,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val bookId = savedStateHandle.toRoute<Route.BookDetail>().id

    private val _state = MutableStateFlow(BookDetailState())
    val state = _state
        .onStart {
            fetchBookDetails()
            checkDownloadStatus()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: BookDetailAction) {
        when(action) {
            is BookDetailAction.OnSelectedBookChange -> {
                _state.update { it.copy(
                    book = action.book
                ) }
            }

            is BookDetailAction.OnDownloadClick -> {
                downloadBook()
            }
            is BookDetailAction.OnReadClick -> {
                val book = state.value.book ?: return
                val fileName = "${book.title.replace(" ", "_")}.pdf"
                downloadManager.getDownloadedFilePath(fileName)?.let { path ->
                    fileOpener.openFile(path)
                }
            }
            else -> Unit
        }
    }

    private fun checkDownloadStatus() {
        val book = state.value.book ?: return
        val fileName = "${book.title.replace(" ", "_")}.pdf"
        _state.update { it.copy(
            isDownloaded = downloadManager.isBookDownloaded(fileName)
        ) }
    }

    private fun downloadBook() {
        val book = state.value.book ?: return
        val downloadUrl = book.downloadUrl ?: return
        val fileName = "${book.title.replace(" ", "_")}.pdf"

        downloadManager.downloadBook(downloadUrl, fileName)
            .onEach { downloadState ->
                when(downloadState) {
                    is DownloadState.Downloading -> {
                        _state.update { it.copy(
                            isDownloading = true,
                            downloadProgress = downloadState.progress
                        ) }
                    }
                    is DownloadState.Finished -> {
                        _state.update { it.copy(
                            isDownloading = false,
                            isDownloaded = true
                        ) }
                        viewModelScope.launch {
                            bookRepository.markAsDownloaded(book, downloadState.path)
                        }
                    }
                    is DownloadState.Failed -> {
                        _state.update { it.copy(
                            isDownloading = false
                        ) }
                    }
                    else -> Unit
                }
            }.launchIn(viewModelScope)
    }

    private fun fetchBookDetails() {
        viewModelScope.launch {
            bookRepository
                .getBookDetails(bookId)
                .onSuccess { book ->
                    _state.update { it.copy(
                        book = book,
                        isLoading = false
                    ) }
                    checkDownloadStatus()
                }
        }
    }
}