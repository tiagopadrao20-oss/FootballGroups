package com.gohan.footballgroups.ui.screens.groups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gohan.footballgroups.domain.model.Group
import com.gohan.footballgroups.domain.usecase.group.CreateGroupUseCase
import com.gohan.footballgroups.domain.usecase.group.DeleteGroupUseCase
import com.gohan.footballgroups.domain.usecase.group.GetGroupsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/** UI state for the groups list screen. */
data class GroupsUiState(
    val groups: List<Group> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

/**
 * ViewModel for [GroupsScreen].
 *
 * Exposes a [StateFlow] of [GroupsUiState] that the screen observes.
 * All business logic is delegated to use cases.
 */
@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val getGroupsUseCase: GetGroupsUseCase,
    private val deleteGroupUseCase: DeleteGroupUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(GroupsUiState())
    val uiState: StateFlow<GroupsUiState> = _uiState.asStateFlow()

    init {
        observeGroups()
    }

    private fun observeGroups() {
        getGroupsUseCase()
            .onEach { groups ->
                _uiState.update { it.copy(groups = groups, isLoading = false, error = null) }
            }
            .catch { e ->
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
            .launchIn(viewModelScope)
    }

    /** Deletes a group. Any error is surfaced in [GroupsUiState.error]. */
    fun deleteGroup(groupId: Long) {
        viewModelScope.launch {
            runCatching { deleteGroupUseCase(groupId) }
                .onFailure { e -> _uiState.update { it.copy(error = e.message) } }
        }
    }

    fun clearError() = _uiState.update { it.copy(error = null) }
}
