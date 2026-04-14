package com.gohan.footballgroups.domain.usecase.group

import com.gohan.footballgroups.domain.model.Group
import com.gohan.footballgroups.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case: observe the full list of groups.
 *
 * Emits a fresh list every time the underlying data changes, allowing the UI
 * to react automatically without manual refreshes.
 */
class GetGroupsUseCase(private val repository: GroupRepository) {

    /**
     * @return A [Flow] that emits [List<Group>] whenever groups change.
     */
    operator fun invoke(): Flow<List<Group>> = repository.getGroups()
}
