package net.yuuzu.diaryapp_composemultiplatform.presentation.detail

import net.yuuzu.diaryapp_composemultiplatform.data.source.local.model.Diary

data class DetailState(
    val diary: Diary? = null,
    val message: String = "",
    val completed: Boolean = false,
)
