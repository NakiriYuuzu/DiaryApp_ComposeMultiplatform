package net.yuuzu.diaryapp_composemultiplatform.presentation.main

import net.yuuzu.diaryapp_composemultiplatform.data.source.local.model.Diary

data class MainState(
    val tags: List<String> = emptyList(),
    val diaries: List<Diary> = emptyList(),
    val recentlyDiaries: List<Diary> = emptyList(),

    val selectedTag: String = "All",
    val selectedDate: Long = 0,
    val isCalendarDialogOpen: Boolean = false,
)
