package net.yuuzu.diaryapp_composemultiplatform.presentation.main

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import net.yuuzu.diaryapp_composemultiplatform.data.repository.DiaryRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel: ViewModel(), KoinComponent {
    private val repository: DiaryRepository by inject()

    private val _state = MutableStateFlow(MainState())
    val state = combine(
        _state,
        repository.getDiaries(),
        repository.getRecentDiaries(10),
    ) { state, diaries, recentDiaries ->
        state.copy(
            tags = diaries
                .map { it.tag }
                .toMutableList()
                .apply { add(0, "All") }
                .toHashSet()
                .toList(),
            diaries = diaries,
            recentlyDiaries = recentDiaries
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), MainState())

    fun onEvent(event: MainEvent) {
        when (event) {
            MainEvent.OnCalendarClicked -> {
                _state.update { it.copy(
                    isCalendarDialogOpen = true
                ) }
            }
            MainEvent.OnCalendarDismissed -> {
                _state.update { it.copy(
                        isCalendarDialogOpen = false
                ) }
            }
            is MainEvent.OnCalendarSelected -> {
                val startDate = event.value
                val endDate = event.value + 86400000L

                viewModelScope.launch {
                    repository.getDiariesByDate(startDate, endDate).collect { diaries ->
                        _state.update { it.copy(
                            selectedDate = event.value,
                            diaries = diaries,
                            isCalendarDialogOpen = false
                        ) }
                    }
                }
            }
            is MainEvent.OnTagSelected -> {
                viewModelScope.launch {
                    if (event.tag != "All") {
                        repository.getDiariesByTag(event.tag).collect { diaries ->
                            _state.update { it.copy(
                                selectedTag = event.tag,
                                diaries = diaries
                            ) }
                        }
                    } else {
                        repository.getDiaries().collect { diaries ->
                            _state.update { it.copy(
                                selectedTag = event.tag,
                                diaries = diaries
                            ) }
                        }
                    }
                }
            }
        }
    }
}