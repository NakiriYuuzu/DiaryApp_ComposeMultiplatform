package net.yuuzu.diaryapp_composemultiplatform.di

import net.yuuzu.diaryapp_composemultiplatform.data.repository.DiaryRepository
import net.yuuzu.diaryapp_composemultiplatform.data.source.DiaryDataSource
import net.yuuzu.diaryapp_composemultiplatform.data.source.local.DiaryLocalDataSourceImpl
import net.yuuzu.diaryapp_composemultiplatform.database.DiaryDatabase
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

private val dataModule = module {
    single<DiaryDataSource.Local> { DiaryLocalDataSourceImpl(database = DiaryDatabase(driver = get())) }
    single { DiaryRepository(localSource = get()) }
}

private val sharedModules = listOf(dataModule)

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(sharedModules)
}