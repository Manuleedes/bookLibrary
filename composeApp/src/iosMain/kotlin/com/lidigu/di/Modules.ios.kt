package com.lidigu.di

import com.lidigu.book.data.database.DatabaseFactory
import com.lidigu.core.domain.IOSFileOpener
import com.lidigu.core.domain.IOSFileSaver
import com.lidigu.core.domain.FileOpener
import com.lidigu.core.domain.FileSaver
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
        single { DatabaseFactory() }
        single { IOSFileSaver() }.bind<FileSaver>()
        single { IOSFileOpener() }.bind<FileOpener>()
    }