package com.gohan.footballgroups.domain.usecase.event

import com.gohan.footballgroups.domain.model.FootballEvent
import com.gohan.footballgroups.domain.repository.EventRepository

/**
 * Use case: validate and persist a new football event.
 *
 * Business rules:
 * - Title must not be blank.
 * - Date must be in the future.
 * - Location must not be blank.
 * - Max players must be between 2 and 50.
 */
class CreateEventUseCase(private val repository: EventRepository) {

    /**
     * @param event The event to create. [FootballEvent.id] should be 0.
     * @return The auto-generated id of the created event.
     * @throws IllegalArgumentException if any validation rule is violated.
     */
    suspend operator fun invoke(event: FootballEvent): Long {
        require(event.title.isNotBlank()) { "Event title must not be blank." }
        require(event.dateTime > System.currentTimeMillis()) { "Event date must be in the future." }
        require(event.location.isNotBlank()) { "Event location must not be blank." }
        require(event.maxPlayers in 2..50) { "Max players must be between 2 and 50." }
        return repository.createEvent(event)
    }
}
