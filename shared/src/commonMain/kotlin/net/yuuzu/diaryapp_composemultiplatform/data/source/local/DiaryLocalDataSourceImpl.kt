package net.yuuzu.diaryapp_composemultiplatform.data.source.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.yuuzu.diaryapp_composemultiplatform.data.source.DiaryDataSource
import net.yuuzu.diaryapp_composemultiplatform.data.source.local.mapper.toDiary
import net.yuuzu.diaryapp_composemultiplatform.data.source.local.model.Diary
import net.yuuzu.diaryapp_composemultiplatform.database.DiaryDatabase

class DiaryLocalDataSourceImpl(
    database: DiaryDatabase
): DiaryDataSource.Local {
    private val queries = database.diaryQueries

    override fun getDiary(id: Long): Flow<Diary> {
        return queries
            .getDiary(id)
            .asFlow()
            .map { diaryEntity ->
                diaryEntity.executeAsOne().toDiary()
            }
    }

    override fun getDiaries(): Flow<List<Diary>> {
        return queries
            .getDiaries()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { diaryEntities ->
                diaryEntities
                    .map { it.toDiary() }
            }
    }

    override fun getRecentDiaries(amount: Long): Flow<List<Diary>> {
        return queries
            .getRecentDiaries(amount)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { diaryEntities ->
                diaryEntities.map { it.toDiary() }
            }
    }

    override fun getDiariesByTag(tag: String): Flow<List<Diary>> {
        return queries
            .getDiariesByTag(tag = tag)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { diaryEntities ->
                diaryEntities
                    .map { it.toDiary() }
            }
    }

    override fun getDiariesByDate(startDate: Long, endDate: Long): Flow<List<Diary>> {
        return queries
            .getDiariesByDate(startDate = startDate, endDate = endDate)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { diaryEntities ->
                diaryEntities
                    .map { it.toDiary() }
            }
    }

    override suspend fun insertDiary(diary: Diary) {
        queries.insertDiaryEntity(
            id = diary.id,
            tag = diary.tag,
            title = diary.title,
            content = diary.content,
            imagePath = null,
            createdAt = diary.date,
        )
    }

    override suspend fun deleteDiary(id: Long) {
        queries.deleteDiary(id = id)
    }
}