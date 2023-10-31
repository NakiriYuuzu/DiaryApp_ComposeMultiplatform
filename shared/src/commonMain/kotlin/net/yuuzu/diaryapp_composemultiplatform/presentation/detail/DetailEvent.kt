package net.yuuzu.diaryapp_composemultiplatform.presentation.detail

sealed interface DetailEvent {
    data class OnTitleChanged(val value: String) : DetailEvent
    data class OnContentChanged(val value: String) : DetailEvent
    data class OnTagChanged(val value: String) : DetailEvent
    data class OnPhotoPicked(val value: ByteArray) : DetailEvent

    data object OnSaveClicked : DetailEvent
    data object OnDeleteClicked : DetailEvent
}