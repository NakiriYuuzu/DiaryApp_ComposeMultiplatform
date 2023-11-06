package net.yuuzu.diaryapp_composemultiplatform.data.source.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope
import kotlinx.datetime.Clock
import net.yuuzu.diaryapp_composemultiplatform.data.source.DiaryDataSource
import net.yuuzu.diaryapp_composemultiplatform.data.source.local.mapper.toDiary
import net.yuuzu.diaryapp_composemultiplatform.data.source.local.model.Diary
import net.yuuzu.diaryapp_composemultiplatform.database.DiaryDatabase
import net.yuuzu.diaryapp_composemultiplatform.utils.camoCase
import net.yuuzu.diaryapp_composemultiplatform.utils.koin.ImageStorage

class DiaryLocalDataSourceImpl(
    database: DiaryDatabase,
    private val imageStorage: ImageStorage
): DiaryDataSource.Local {
    private val queries = database.diaryQueries

    override fun getDiary(id: Long): Flow<Diary> {
        return queries
            .getDiary(id)
            .asFlow()
            .map { diaryEntity ->
                diaryEntity.executeAsOne().toDiary(imageStorage)
            }
    }

    override fun getDiaries(): Flow<List<Diary>> {
        return queries
            .getDiaries()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { diaryEntities ->
                supervisorScope {
                    diaryEntities
                        .map {
                            async { it.toDiary(imageStorage) }
                        }
                        .map { it.await() }
                }
            }
    }

    override fun getRecentDiaries(amount: Long): Flow<List<Diary>> {
        return queries
            .getRecentDiaries(amount)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { diaryEntities ->
                supervisorScope {
                    diaryEntities
                        .map {
                            async { it.toDiary(imageStorage) }
                        }
                        .map { it.await() }
                }
            }
    }

    override fun getDiariesByTag(tag: String): Flow<List<Diary>> {
        return queries
            .getDiariesByTag(tag = tag)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { diaryEntities ->
                supervisorScope {
                    diaryEntities
                        .map {
                            async { it.toDiary(imageStorage) }
                        }
                        .map { it.await() }
                }
            }
    }

    override fun getDiariesByDate(startDate: Long, endDate: Long): Flow<List<Diary>> {
        return queries
            .getDiariesByDate(startDate = startDate, endDate = endDate)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { diaryEntities ->
                supervisorScope {
                    diaryEntities
                        .map {
                            async { it.toDiary(imageStorage) }
                        }
                        .map { it.await() }
                }
            }
    }

    override suspend fun insertDiary(diary: Diary) {
        val imagePath = diary.imageBytes?.let {
            imageStorage.saveImage(it)
        }

        queries.insertDiaryEntity(
            id = diary.id,
            tag = diary.tag.camoCase(),
            title = diary.title,
            content = diary.content,
            imagePath = imagePath,
            createdAt = Clock.System.now().toEpochMilliseconds(),
        )
    }

    override suspend fun deleteDiary(id: Long) {
        val entity = queries.getDiary(id).executeAsOneOrNull()
        entity?.imagePath?.let { imageStorage.deleteImage(it) }
        queries.deleteDiary(id = id)
    }
}