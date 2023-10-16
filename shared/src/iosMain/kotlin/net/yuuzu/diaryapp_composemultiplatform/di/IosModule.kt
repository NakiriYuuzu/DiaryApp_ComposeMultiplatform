package net.yuuzu.diaryapp_composemultiplatform.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import net.yuuzu.diaryapp_composemultiplatform.database.DiaryDatabase
import org.koin.dsl.module

internal val appModule = module {
    single<SqlDriver> {
        NativeSqliteDriver(DiaryDatabase.Schema, "diary.db")
    }
}