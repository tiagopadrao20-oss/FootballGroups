package com.gohan.footballgroups.data.mapper

import com.gohan.footballgroups.data.local.entity.EventParticipantEntity
import com.gohan.footballgroups.data.local.entity.FootballEventEntity
import com.gohan.footballgroups.domain.model.EventParticipant
import com.gohan.footballgroups.domain.model.FootballEvent

// ── Event mappers ─────────────────────────────────────────────────────────────

/** Converts a [FootballEventEntity] + participants to the domain [FootballEvent]. */
fun FootballEventEntity.toDomain(
    participants: List<EventParticipantEntity> = emptyList()
): FootballEvent = FootballEvent(
    id = id,
    groupId = groupId,
    title = title,
    dateTime = dateTime,
    location = location,
    maxPlayers = maxPlayers,
    notes = notes,
    createdBy = createdBy,
    createdAt = createdAt,
    participants = participants.map { it.toDomain() }
)

/** Converts a domain [FootballEvent] to its Room entity (participants stored separately). */
fun FootballEvent.toEntity(): FootballEventEntity = FootballEventEntity(
    id = id,
    groupId = groupId,
    title = title,
    dateTime = dateTime,
    location = location,
    maxPlayers = maxPlayers,
    notes = notes,
    createdBy = createdBy,
    createdAt = createdAt
)

// ── Participant mappers ───────────────────────────────────────────────────────

/** Converts an [EventParticipantEntity] to the domain [EventParticipant]. */
fun EventParticipantEntity.toDomain(): EventParticipant = EventParticipant(
    id = id,
    eventId = eventId,
    playerName = playerName,
    confirmedAt = confirmedAt
)

/** Converts a domain [EventParticipant] to its Room entity. */
fun EventParticipant.toEntity(): EventParticipantEntity = EventParticipantEntity(
    id = id,
    eventId = eventId,
    playerName = playerName,
    confirmedAt = confirmedAt
)
