package com.gohan.footballgroups.data.mapper

import com.gohan.footballgroups.data.local.entity.GroupEntity
import com.gohan.footballgroups.data.local.entity.PlayerEntity
import com.gohan.footballgroups.domain.model.Group
import com.gohan.footballgroups.domain.model.Player

// ── Group mappers ─────────────────────────────────────────────────────────────

/** Converts a [GroupEntity] + list of [PlayerEntity] to the domain [Group]. */
fun GroupEntity.toDomain(players: List<PlayerEntity> = emptyList()): Group = Group(
    id = id,
    name = name,
    description = description,
    createdBy = createdBy,
    createdAt = createdAt,
    members = players.map { it.toDomain() }
)

/** Converts a domain [Group] to its Room entity (members are stored separately). */
fun Group.toEntity(): GroupEntity = GroupEntity(
    id = id,
    name = name,
    description = description,
    createdBy = createdBy,
    createdAt = createdAt
)

// ── Player mappers ────────────────────────────────────────────────────────────

/** Converts a [PlayerEntity] to the domain [Player]. */
fun PlayerEntity.toDomain(): Player = Player(
    id = id,
    name = name,
    groupId = groupId,
    joinedAt = joinedAt
)

/** Converts a domain [Player] to its Room entity. */
fun Player.toEntity(): PlayerEntity = PlayerEntity(
    id = id,
    name = name,
    groupId = groupId,
    joinedAt = joinedAt
)
