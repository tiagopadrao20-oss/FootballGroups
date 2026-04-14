package com.gohan.footballgroups.domain.usecase.group

import com.gohan.footballgroups.domain.repository.GroupRepository

/**
 * Use case: permanently delete a group and all its data.
 */
class DeleteGroupUseCase(private val repository: GroupRepository) {

    /**
     * @param groupId Primary key of the group to delete.
     */
    suspend operator fun invoke(groupId: Long) = repository.deleteGroup(groupId)
}
