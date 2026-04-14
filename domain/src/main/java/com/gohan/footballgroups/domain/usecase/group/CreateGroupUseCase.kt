package com.gohan.footballgroups.domain.usecase.group

import com.gohan.footballgroups.domain.model.Group
import com.gohan.footballgroups.domain.repository.GroupRepository

/**
 * Use case: validate and persist a new group.
 *
 * Business rules enforced here:
 * - Group name must not be blank.
 * - Creator name must not be blank.
 */
class CreateGroupUseCase(private val repository: GroupRepository) {

    /**
     * @param group The group to create. Its [Group.id] should be 0.
     * @return The auto-generated id of the created group.
     * @throws IllegalArgumentException if validation fails.
     */
    suspend operator fun invoke(group: Group): Long {
        require(group.name.isNotBlank()) { "Group name must not be blank." }
        require(group.createdBy.isNotBlank()) { "Creator name must not be blank." }
        return repository.createGroup(group)
    }
}
