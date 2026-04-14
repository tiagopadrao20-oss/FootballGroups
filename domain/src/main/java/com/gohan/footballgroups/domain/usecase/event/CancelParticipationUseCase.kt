package com.gohan.footballgroups.domain.usecase.event

import com.gohan.footballgroups.domain.repository.EventRepository

/**
 * Use case: cancel a player's participation in an event.
 */
class CancelParticipationUseCase(private val repository: EventRepository) {

    /**
     * @param participantId Primary key of the participation record to remove.
     */
    suspend operator fun invoke(participantId: Long) =
        repository.cancelParticipation(participantId)
}
