package com.gohan.footballgroups.domain.usecase.group

import com.gohan.footballgroups.domain.model.Group
import com.gohan.footballgroups.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case: observe a single group by its id.
 */
class GetGroupByIdUseCase(private val repository: GroupRepository) {

    /**
     * @param groupId Primary key of the desired group.
     * @return A [Flow] emitting the [Group] (with members) or null if not found.
     */
    operator fun invoke(groupId: Long): Flow<Group?> = repository.getGroupById(groupId)
}
