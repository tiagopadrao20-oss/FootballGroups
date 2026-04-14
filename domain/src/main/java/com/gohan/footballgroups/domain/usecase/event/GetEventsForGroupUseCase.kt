package com.gohan.footballgroups.domain.usecase.event

import com.gohan.footballgroups.domain.model.FootballEvent
import com.gohan.footballgroups.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case: observe all events for a specific group.
 */
class GetEventsForGroupUseCase(private val repository: EventRepository) {

    /**
     * @param groupId Primary key of the owning group.
     * @return [Flow] emitting the event list sorted by date ascending.
     */
    operator fun invoke(groupId: Long): Flow<List<FootballEvent>> =
        repository.getEventsForGroup(groupId)
}
