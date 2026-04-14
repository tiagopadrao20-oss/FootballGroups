package com.gohan.footballgroups.domain.usecase.event

import com.gohan.footballgroups.domain.model.FootballEvent
import com.gohan.footballgroups.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case: observe a single event by its id.
 */
class GetEventByIdUseCase(private val repository: EventRepository) {

    /**
     * @param eventId Primary key of the desired event.
     * @return [Flow] emitting the [FootballEvent] (with participants) or null.
     */
    operator fun invoke(eventId: Long): Flow<FootballEvent?> = repository.getEventById(eventId)
}
