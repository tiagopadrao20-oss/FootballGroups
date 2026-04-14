package com.gohan.footballgroups.domain.repository

import com.gohan.footballgroups.domain.model.Group
import com.gohan.footballgroups.domain.model.Player
import kotlinx.coroutines.flow.Flow

/**
 * Contract for all group-related data operations.
 * The data layer provides the concrete implementation.
 */
interface GroupRepository {

    /** Emits the full list of groups, updated whenever the data changes. */
    fun getGroups(): Flow<List<Group>>

    /**
     * Emits a single group with its members, or null if not found.
     * @param groupId Primary key of the target group.
     */
    fun getGroupById(groupId: Long): Flow<Group?>

    /**
     * Persists a new group and returns its generated id.
     * @param group Group to insert (id should be 0).
     */
    suspend fun createGroup(group: Group): Long

    /**
     * Updates an existing group's editable fields.
     * @param group Group with updated values (id must match an existing record).
     */
    suspend fun updateGroup(group: Group)

    /**
     * Permanently deletes a group and all related data.
     * @param groupId Primary key of the group to delete.
     */
    suspend fun deleteGroup(groupId: Long)

    /**
     * Adds a player to an existing group.
     * @param player Player to insert (id should be 0).
     */
    suspend fun addMember(player: Player): Long

    /**
     * Removes a player from a group.
     * @param playerId Primary key of the player to remove.
     */
    suspend fun removeMember(playerId: Long)
}
