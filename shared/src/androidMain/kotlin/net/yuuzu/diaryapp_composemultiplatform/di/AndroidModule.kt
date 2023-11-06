package net.yuuzu.diaryapp_composemultiplatform.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import net.yuuzu.diaryapp_composemultiplatform.database.DiaryDatabase
import net.yuuzu.diaryapp_composemultiplatform.utils.koin.ImageStorage
import net.yuuzu.diaryapp_composemultiplatform.utils.koin.ImageStorageFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = DiaryDatabase.Schema,
            context = androidContext(),
            name = "diary.db"
        )
    }
    single<ImageStorage> { ImageStorageFactory(androidContext()) }
}