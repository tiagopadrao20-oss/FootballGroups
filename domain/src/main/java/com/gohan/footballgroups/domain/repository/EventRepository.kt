package com.gohan.footballgroups.domain.repository

import com.gohan.footballgroups.domain.model.EventParticipant
import com.gohan.footballgroups.domain.model.FootballEvent
import kotlinx.coroutines.flow.Flow

/**
 * Contract for all football-event data operations.
 * The data layer provides the concrete implementation.
 */
interface EventRepository {

    /**
     * Emits all events belonging to a group, sorted by date ascending.
     * @param groupId Primary key of the owning group.
     */
    fun getEventsForGroup(groupId: Long): Flow<List<FootballEvent>>

    /**
     * Emits a single event with its participants, or null if not found.
     * @param eventId Primary key of the target event.
     */
    fun getEventById(eventId: Long): Flow<FootballEvent?>

    /**
     * Persists a new event and returns its generated id.
     * @param event FootballEvent to insert (id should be 0).
     */
    suspend fun createEvent(event: FootballEvent): Long

    /**
     * Updates an existing event's editable fields.
     * @param event Event with updated values (id must match an existing record).
     */
    suspend fun updateEvent(event: FootballEvent)

    /**
     * Permanently deletes an event and all its participants.
     * @param eventId Primary key of the event to delete.
     */
    suspend fun deleteEvent(eventId: Long)

    /**
     * Records a player's confirmation for an event.
     * @param participant Participation record to insert (id should be 0).
     */
    suspend fun confirmParticipation(participant: EventParticipant): Long

    /**
     * Removes a player's confirmation from an event.
     * @param participantId Primary key of the participation record to delete.
     */
    suspend fun cancelParticipation(participantId: Long)
}
