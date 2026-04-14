package com.gohan.footballgroups.data.repository

import com.gohan.footballgroups.data.local.dao.EventDao
import com.gohan.footballgroups.data.mapper.toDomain
import com.gohan.footballgroups.data.mapper.toEntity
import com.gohan.footballgroups.domain.model.EventParticipant
import com.gohan.footballgroups.domain.model.FootballEvent
import com.gohan.footballgroups.domain.repository.EventRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

/**
 * Concrete implementation of [EventRepository] backed by Room.
 *
 * @param eventDao DAO injected by Hilt via [DatabaseModule].
 */
class EventRepositoryImpl(private val eventDao: EventDao) : EventRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getEventsForGroup(groupId: Long): Flow<List<FootballEvent>> =
        eventDao.getEventsForGroup(groupId).flatMapLatest { entities ->
            if (entities.isEmpty()) return@flatMapLatest flowOf(emptyList())
            combine(
                entities.map { entity ->
                    eventDao.getParticipantsForEvent(entity.id).map { participants ->
                        entity.toDomain(participants)
                    }
                }
            ) { it.toList() }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getEventById(eventId: Long): Flow<FootballEvent?> =
        eventDao.getEventById(eventId).flatMapLatest { entity ->
            entity?.let {
                eventDao.getParticipantsForEvent(eventId).map { participants ->
                    it.toDomain(participants)
                }
            } ?: flowOf(null)
        }

    override suspend fun createEvent(event: FootballEvent): Long =
        eventDao.insertEvent(event.toEntity())

    override suspend fun updateEvent(event: FootballEvent) =
        eventDao.updateEvent(event.toEntity())

    override suspend fun deleteEvent(eventId: Long) =
        eventDao.deleteEvent(eventId)

    override suspend fun confirmParticipation(participant: EventParticipant): Long =
        eventDao.insertParticipant(participant.toEntity())

    override suspend fun cancelParticipation(participantId: Long) =
        eventDao.deleteParticipant(participantId)
}
