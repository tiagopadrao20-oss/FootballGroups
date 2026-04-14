package com.gohan.footballgroups.data.local.dao

import androidx.room.*
import com.gohan.footballgroups.data.local.entity.GroupEntity
import com.gohan.footballgroups.data.local.entity.PlayerEntity
import kotlinx.coroutines.flow.Flow

/**
 * Room DAO for group and player operations.
 */
@Dao
interface GroupDao {

    // ── Groups ──────────────────────────────────────────────────────────────

    /** Observe all groups ordered by most recently created. */
    @Query("SELECT * FROM groups ORDER BY createdAt DESC")
    fun getAllGroups(): Flow<List<GroupEntity>>

    /** Observe a single group by primary key. */
    @Query("SELECT * FROM groups WHERE id = :groupId")
    fun getGroupById(groupId: Long): Flow<GroupEntity?>

    /** Insert a group and return its generated id. */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertGroup(group: GroupEntity): Long

    /** Update editable fields of an existing group. */
    @Update
    suspend fun updateGroup(group: GroupEntity)

    /** Delete a group (cascades to players and events). */
    @Query("DELETE FROM groups WHERE id = :groupId")
    suspend fun deleteGroup(groupId: Long)

    // ── Players ─────────────────────────────────────────────────────────────

    /** Observe all players belonging to a group. */
    @Query("SELECT * FROM players WHERE groupId = :groupId ORDER BY joinedAt ASC")
    fun getPlayersForGroup(groupId: Long): Flow<List<PlayerEntity>>

    /** Insert a player and return its generated id. */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPlayer(player: PlayerEntity): Long

    /** Remove a player by primary key. */
    @Query("DELETE FROM players WHERE id = :playerId")
    suspend fun deletePlayer(playerId: Long)
}
