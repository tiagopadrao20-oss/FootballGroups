package com.gohan.footballgroups.ui.screens.groups

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gohan.footballgroups.domain.model.FootballEvent
import com.gohan.footballgroups.domain.model.Group
import com.gohan.footballgroups.domain.usecase.event.GetEventsForGroupUseCase
import com.gohan.footballgroups.domain.usecase.group.AddMemberUseCase
import com.gohan.footballgroups.domain.usecase.group.GetGroupByIdUseCase
import com.gohan.footballgroups.domain.model.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/** UI state for the group-detail screen. */
data class GroupDetailUiState(
    val group: Group? = null,
    val upcomingEvents: List<FootballEvent> = emptyList(),
    val pastEvents: List<FootballEvent> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

/**
 * ViewModel for [GroupDetailScreen].
 *
 * Reads the groupId from [SavedStateHandle] so it survives process death.
 */
@HiltViewModel
class GroupDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    private val getEventsForGroupUseCase: GetEventsForGroupUseCase,
    private val addMemberUseCase: AddMemberUseCase
) : ViewModel() {

    private val groupId: Long = checkNotNull(savedStateHandle["groupId"])

    private val _uiState = MutableStateFlow(GroupDetailUiState())
    val uiState: StateFlow<GroupDetailUiState> = _uiState.asStateFlow()

    init {
        observeGroup()
        observeEvents()
    }

    private fun observeGroup() {
        getGroupByIdUseCase(groupId)
            .onEach { group -> _uiState.update { it.copy(group = group, isLoading = false) } }
            .catch { e -> _uiState.update { it.copy(isLoading = false, error = e.message) } }
            .launchIn(viewModelScope)
    }

    private fun observeEvents() {
        getEventsForGroupUseCase(groupId)
            .onEach { events ->
                val now = System.currentTimeMillis()
                _uiState.update {
                    it.copy(
                        upcomingEvents = events.filter { e -> e.dateTime > now },
                        pastEvents = events.filter { e -> e.dateTime <= now }.sortedByDescending { e -> e.dateTime }
                    )
                }
            }
            .catch { e -> _uiState.update { it.copy(error = e.message) } }
            .launchIn(viewModelScope)
    }

    /** Adds a player to this group. */
    fun addMember(playerName: String) {
        viewModelScope.launch {
            runCatching {
                addMemberUseCase(Player(name = playerName.trim(), groupId = groupId))
            }.onFailure { e -> _uiState.update { it.copy(error = e.message) } }
        }
    }

    fun clearError() = _uiState.update { it.copy(error = null) }
}
