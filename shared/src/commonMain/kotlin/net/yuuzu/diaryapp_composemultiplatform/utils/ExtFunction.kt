package net.yuuzu.diaryapp_composemultiplatform.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.yuuzu.diaryapp_composemultiplatform.data.source.local.model.Diary

fun Diary.toDateTimeString(): String {
    if (date == null) return "NAN"
    val timeZone = TimeZone.currentSystemDefault()
    val result = Instant.fromEpochMilliseconds(this.date).toLocalDateTime(timeZone)
    val date = result.date
    val time = result.time.toString().substring(0, 5)
    return "$date $time"
}

fun String.camoCase(): String {
    return this.first().uppercase() + this.substring(1)
}