package net.yuuzu.diaryapp_composemultiplatform.data.source

import kotlinx.coroutines.flow.Flow
import net.yuuzu.diaryapp_composemultiplatform.data.source.local.model.Diary

interface DiaryDataSource {
    interface Local {
        fun getDiary(id: Long): Flow<Diary>
        fun getDiaries(): Flow<List<Diary>>
        fun getDiariesByTag(tag: String): Flow<List<Diary>>
        fun getDiariesByDate(startDate: Long, endDate: Long): Flow<List<Diary>>
        suspend fun insertDiary(diary: Diary)
        suspend fun deleteDiary(id: Long)
    }
}