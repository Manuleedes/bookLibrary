package com.lidigu.di

import com.lidigu.book.data.database.DatabaseFactory
import com.lidigu.core.domain.DesktopFileOpener
import com.lidigu.core.domain.DesktopFileSaver
import com.lidigu.core.domain.FileOpener
import com.lidigu.core.domain.FileSaver
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single { DatabaseFactory() }
        single { DesktopFileSaver() }.bind<FileSaver>()
        single { DesktopFileOpener() }.bind<FileOpener>()
    }