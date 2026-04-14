package com.gohan.footballgroups.ui.screens.groups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gohan.footballgroups.domain.model.Group
import com.gohan.footballgroups.domain.usecase.group.CreateGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/** UI state for the create-group screen. */
data class CreateGroupUiState(
    val isLoading: Boolean = false,
    val createdGroupId: Long? = null,
    val error: String? = null
)

/**
 * ViewModel for [CreateGroupScreen].
 */
@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val createGroupUseCase: CreateGroupUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateGroupUiState())
    val uiState: StateFlow<CreateGroupUiState> = _uiState.asStateFlow()

    /**
     * Validates inputs and creates the group.
     *
     * @param name        Display name entered by the user.
     * @param description Optional description.
     * @param createdBy   Name of the person creating the group.
     */
    fun createGroup(name: String, description: String, createdBy: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            runCatching {
                createGroupUseCase(
                    Group(name = name.trim(), description = description.trim(), createdBy = createdBy.trim())
                )
            }
                .onSuccess { id -> _uiState.update { it.copy(isLoading = false, createdGroupId = id) } }
                .onFailure { e -> _uiState.update { it.copy(isLoading = false, error = e.message) } }
        }
    }

    fun clearError() = _uiState.update { it.copy(error = null) }
}
