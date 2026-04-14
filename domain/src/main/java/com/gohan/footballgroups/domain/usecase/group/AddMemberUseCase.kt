package com.gohan.footballgroups.domain.usecase.group

import com.gohan.footballgroups.domain.model.Player
import com.gohan.footballgroups.domain.repository.GroupRepository

/**
 * Use case: add a new member to an existing group.
 *
 * Business rules:
 * - Player name must not be blank.
 */
class AddMemberUseCase(private val repository: GroupRepository) {

    /**
     * @param player The player to add. [Player.id] should be 0.
     * @return The auto-generated id of the new player record.
     * @throws IllegalArgumentException if validation fails.
     */
    suspend operator fun invoke(player: Player): Long {
        require(player.name.isNotBlank()) { "Player name must not be blank." }
        return repository.addMember(player)
    }
}
