package net.yuuzu.diaryapp_composemultiplatform.data.source.local.mapper

import net.yuuzu.diaryapp_composemultiplatform.data.source.local.model.Diary
import net.yuuzu.diaryapp_composemultiplatform.utils.koin.ImageStorage
import net.yuuzu.diaryappcomposemultiplatform.database.DiaryEntity

suspend fun DiaryEntity.toDiary(imageStorage: ImageStorage): Diary {
    return Diary(
        id = id,
        tag = tag,
        title = title,
        content = content,
        imageBytes = imagePath?.let { imageStorage.getImage(it) },
        date = createdAt
    )
}