package com.gohan.footballgroups.domain.model

/**
 * Represents a member of a [Group].
 *
 * @property id      Unique identifier (0 = not yet persisted).
 * @property name    Display name shown throughout the app.
 * @property groupId Foreign key linking the player to their group.
 * @property joinedAt Unix epoch milliseconds when the player was added.
 */
data class Player(
    val id: Long = 0,
    val name: String,
    val groupId: Long,
    val joinedAt: Long = System.currentTimeMillis()
)
