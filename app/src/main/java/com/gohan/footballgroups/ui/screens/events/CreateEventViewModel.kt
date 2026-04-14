package com.gohan.footballgroups.ui.screens.events

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gohan.footballgroups.domain.model.FootballEvent
import com.gohan.footballgroups.domain.usecase.event.CreateEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/** UI state for the create-event screen. */
data class CreateEventUiState(
    val isLoading: Boolean = false,
    val createdEventId: Long? = null,
    val error: String? = null
)

/**
 * ViewModel for [CreateEventScreen].
 */
@HiltViewModel
class CreateEventViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createEventUseCase: CreateEventUseCase
) : ViewModel() {

    val groupId: Long = checkNotNull(savedStateHandle["groupId"])

    private val _uiState = MutableStateFlow(CreateEventUiState())
    val uiState: StateFlow<CreateEventUiState> = _uiState.asStateFlow()

    /**
     * Validates and creates the event.
     *
     * @param title      Event title.
     * @param dateTime   Unix epoch millis for the event start.
     * @param location   Venue or address.
     * @param maxPlayers Maximum number of allowed participants.
     * @param notes      Optional notes.
     * @param createdBy  Name of the organiser.
     */
    fun createEvent(
        title: String,
        dateTime: Long,
        location: String,
        maxPlayers: Int,
        notes: String,
        createdBy: String
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            runCatching {
                createEventUseCase(
                    FootballEvent(
                        groupId = groupId,
                        title = title.trim(),
                        dateTime = dateTime,
                        location = location.trim(),
                        maxPlayers = maxPlayers,
                        notes = notes.trim(),
                        createdBy = createdBy.trim()
                    )
                )
            }
                .onSuccess { id -> _uiState.update { it.copy(isLoading = false, createdEventId = id) } }
                .onFailure { e -> _uiState.update { it.copy(isLoading = false, error = e.message) } }
        }
    }

    fun clearError() = _uiState.update { it.copy(error = null) }
}
