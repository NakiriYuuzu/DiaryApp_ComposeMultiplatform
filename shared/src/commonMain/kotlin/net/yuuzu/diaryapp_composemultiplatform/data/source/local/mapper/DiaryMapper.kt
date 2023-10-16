package net.yuuzu.diaryapp_composemultiplatform.data.source.local.mapper

import net.yuuzu.diaryapp_composemultiplatform.data.source.local.model.Diary
import net.yuuzu.diaryappcomposemultiplatform.database.DiaryEntity

fun DiaryEntity.toDiary(): Diary {
    return Diary(
        id = id,
        tag = tag,
        title = title,
        content = content,
        imageBytes = null,
        date = createdAt
    )
}