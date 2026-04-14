package com.gohan.footballgroups.domain.usecase.event

import com.gohan.footballgroups.domain.model.EventParticipant
import com.gohan.footballgroups.domain.model.FootballEvent
import com.gohan.footballgroups.domain.repository.EventRepository

/**
 * Use case: confirm a player's participation in an event.
 *
 * Business rules:
 * - Player name must not be blank.
 * - The event must not be full.
 * - The player must not already be confirmed.
 *
 * @param eventRepository Used to persist the participation record.
 */
class ConfirmParticipationUseCase(private val eventRepository: EventRepository) {

    /**
     * @param event   The event to join (must include current participants list).
     * @param playerName Name of the player confirming participation.
     * @return The auto-generated id of the [EventParticipant] record.
     * @throws IllegalStateException if the event is full or the player is already confirmed.
     * @throws IllegalArgumentException if the player name is blank.
     */
    suspend operator fun invoke(event: FootballEvent, playerName: String): Long {
        require(playerName.isNotBlank()) { "Player name must not be blank." }
        check(!event.isFull) { "Event is already full (${event.maxPlayers} players)." }
        check(event.participants.none { it.playerName == playerName }) {
            "$playerName is already confirmed for this event."
        }

        val participant = EventParticipant(
            eventId = event.id,
            playerName = playerName
        )
        return eventRepository.confirmParticipation(participant)
    }
}
