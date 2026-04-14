package com.gohan.footballgroups.data.local.dao

import androidx.room.*
import com.gohan.footballgroups.data.local.entity.EventParticipantEntity
import com.gohan.footballgroups.data.local.entity.FootballEventEntity
import kotlinx.coroutines.flow.Flow

/**
 * Room DAO for event and participant operations.
 */
@Dao
interface EventDao {

    // ── Events ───────────────────────────────────────────────────────────────

    /** Observe all events for a group ordered by date ascending. */
    @Query("SELECT * FROM events WHERE groupId = :groupId ORDER BY dateTime ASC")
    fun getEventsForGroup(groupId: Long): Flow<List<FootballEventEntity>>

    /** Observe a single event by primary key. */
    @Query("SELECT * FROM events WHERE id = :eventId")
    fun getEventById(eventId: Long): Flow<FootballEventEntity?>

    /** Insert an event and return its generated id. */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertEvent(event: FootballEventEntity): Long

    /** Update editable fields of an existing event. */
    @Update
    suspend fun updateEvent(event: FootballEventEntity)

    /** Delete an event (cascades to participants). */
    @Query("DELETE FROM events WHERE id = :eventId")
    suspend fun deleteEvent(eventId: Long)

    // ── Participants ─────────────────────────────────────────────────────────

    /** Observe all participants for an event ordered by confirmation time. */
    @Query(
        "SELECT * FROM event_participants WHERE eventId = :eventId ORDER BY confirmedAt ASC"
    )
    fun getParticipantsForEvent(eventId: Long): Flow<List<EventParticipantEntity>>

    /** Insert a participation record and return its generated id. */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertParticipant(participant: EventParticipantEntity): Long

    /** Remove a participation record by primary key. */
    @Query("DELETE FROM event_participants WHERE id = :participantId")
    suspend fun deleteParticipant(participantId: Long)
}
