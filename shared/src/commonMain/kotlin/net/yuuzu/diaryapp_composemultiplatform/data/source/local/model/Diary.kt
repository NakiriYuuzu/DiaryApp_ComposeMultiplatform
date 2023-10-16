package net.yuuzu.diaryapp_composemultiplatform.data.source.local.model

data class Diary(
    val id: Long,
    val tag: String,
    val title: String,
    val content: String,
    val imageBytes: ByteArray?,
    val date: Long
)
