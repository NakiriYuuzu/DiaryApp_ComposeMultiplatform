package net.yuuzu.diaryapp_composemultiplatform.data.repository

import net.yuuzu.diaryapp_composemultiplatform.data.source.DiaryDataSource
import net.yuuzu.diaryapp_composemultiplatform.data.source.local.model.Diary
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DiaryRepository(private val localSource: DiaryDataSource.Local) {

    fun getDiary(id: Long) = localSource.getDiary(id)
    fun getDiaries() = localSource.getDiaries()
    fun getRecentDiaries(amount: Long) = localSource.getRecentDiaries(amount)
    fun getDiariesByTag(tag: String) = localSource.getDiariesByTag(tag)
    fun getDiariesByDate(startDate: Long, endDate: Long) = localSource.getDiariesByDate(startDate, endDate)
    suspend fun insertDiary(diary: Diary) = localSource.insertDiary(diary)
    suspend fun deleteDiary(id: Long) = localSource.deleteDiary(id)
}