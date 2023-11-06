package net.yuuzu.diaryapp_composemultiplatform.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import net.yuuzu.diaryapp_composemultiplatform.data.repository.DiaryRepository
import net.yuuzu.diaryapp_composemultiplatform.data.source.local.model.Diary
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class DetailViewModel(
    private val diaryId: Long
) : ViewModel(), KoinComponent {
    private val repository: DiaryRepository by inject()

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()

    var newDiary: Diary? by mutableStateOf(null)
        private set

    init {
        if (newDiary == null) {
            if (diaryId > 0) {
                println(diaryId)
                viewModelScope.launch {
                    repository.getDiary(diaryId).collect { diary ->
                        newDiary = diary
                    }
                }
            } else {
                newDiary = Diary.empty
            }
        }
    }

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.OnTitleChanged -> {
                newDiary = newDiary?.copy(
                    title = event.value
                )
            }

            is DetailEvent.OnContentChanged -> {
                newDiary = newDiary?.copy(
                    content = event.value
                )
            }

            is DetailEvent.OnTagChanged -> {
                newDiary = newDiary?.copy(
                    tag = event.value
                )
            }

            is DetailEvent.OnPhotoPicked -> {
                newDiary = newDiary?.copy(
                    imageBytes = event.value
                )
            }

            DetailEvent.OnDeleteClicked -> {
                _state.update {
                    it.copy(
                        completed = true
                    )
                }
                viewModelScope.launch { repository.deleteDiary(diaryId) }
            }

            DetailEvent.OnSaveClicked -> {
                newDiary?.let { diary ->
                    if (diary.title.isBlank()) {
                        _state.update { it.copy(
                            message = "Title cannot be empty"
                        ) }
                        return
                    }
                    if (diary.content.isBlank()) {
                        _state.update { it.copy(
                            message = "Content cannot be empty"
                        ) }
                        return
                    }
                    if (diary.tag.isBlank()) {
                        _state.update { it.copy(
                            message = "Tag cannot be empty"
                        ) }
                        return
                    }
                    viewModelScope.launch {
                        repository.insertDiary(diary)
                        newDiary = null
                        _state.update { it.copy(
                            completed = true
                        ) }
                    }
                }
            }
        }
    }
}