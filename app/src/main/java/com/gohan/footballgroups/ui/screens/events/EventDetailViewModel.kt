package com.gohan.footballgroups.ui.screens.events

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gohan.footballgroups.domain.model.FootballEvent
import com.gohan.footballgroups.domain.usecase.event.CancelParticipationUseCase
import com.gohan.footballgroups.domain.usecase.event.ConfirmParticipationUseCase
import com.gohan.footballgroups.domain.usecase.event.GetEventByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/** UI state for the event-detail screen. */
data class EventDetailUiState(
    val event: FootballEvent? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

/**
 * ViewModel for [EventDetailScreen].
 *
 * Handles participation confirmation and cancellation.
 */
@HiltViewModel
class EventDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getEventByIdUseCase: GetEventByIdUseCase,
    private val confirmParticipationUseCase: ConfirmParticipationUseCase,
    private val cancelParticipationUseCase: CancelParticipationUseCase
) : ViewModel() {

    private val eventId: Long = checkNotNull(savedStateHandle["eventId"])

    private val _uiState = MutableStateFlow(EventDetailUiState())
    val uiState: StateFlow<EventDetailUiState> = _uiState.asStateFlow()

    init {
        observeEvent()
    }

    private fun observeEvent() {
        getEventByIdUseCase(eventId)
            .onEach { event -> _uiState.update { it.copy(event = event, isLoading = false) } }
            .catch { e -> _uiState.update { it.copy(isLoading = false, error = e.message) } }
            .launchIn(viewModelScope)
    }

    /** Confirms participation for [playerName]. Surfaces any error in [uiState]. */
    fun confirmParticipation(playerName: String) {
        val event = _uiState.value.event ?: return
        viewModelScope.launch {
            runCatching { confirmParticipationUseCase(event, playerName) }
                .onFailure { e -> _uiState.update { it.copy(error = e.message) } }
        }
    }

    /** Cancels a player's participation by their participant record id. */
    fun cancelParticipation(participantId: Long) {
        viewModelScope.launch {
            runCatching { cancelParticipationUseCase(participantId) }
                .onFailure { e -> _uiState.update { it.copy(error = e.message) } }
        }
    }

    fun clearError() = _uiState.update { it.copy(error = null) }
}
