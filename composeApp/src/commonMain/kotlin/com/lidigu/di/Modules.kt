package com.lidigu.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.lidigu.book.data.database.DatabaseFactory
import com.lidigu.book.data.database.FavoriteBookDatabase
import com.lidigu.book.data.download.KtorDownloadManager
import com.lidigu.book.data.network.KtorRemoteBookDataSource
import com.lidigu.book.data.network.RemoteBookDataSource
import com.lidigu.book.data.repository.DefaultBookRepository
import com.lidigu.book.domain.BookRepository
import com.lidigu.book.domain.DownloadManager
import com.lidigu.book.presentation.SelectedBookViewModel
import com.lidigu.book.presentation.book_detail.BookDetailViewModel
import com.lidigu.book.presentation.book_list.BookListViewModel
import com.lidigu.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()
    singleOf(::KtorDownloadManager).bind<DownloadManager>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<FavoriteBookDatabase>().favoriteBookDao }

    viewModelOf(::BookListViewModel)
    viewModelOf(::BookDetailViewModel)
    viewModelOf(::SelectedBookViewModel)
}