package net.yuuzu.diaryapp_composemultiplatform.presentation.main

sealed interface MainEvent {
    data object OnCalendarClicked: MainEvent
    data object OnCalendarDismissed: MainEvent
    data class OnCalendarSelected(val value: Long): MainEvent
    data class OnTagSelected(val tag: String): MainEvent
}