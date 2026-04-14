package com.gohan.footballgroups.domain.model

/**
 * Represents a group of friends that organises football matches.
 *
 * @property id       Unique identifier (0 = not yet persisted).
 * @property name     Display name of the group.
 * @property description Short description shown on the group card.
 * @property createdBy Name of the member who created the group.
 * @property createdAt Unix epoch milliseconds.
 * @property members  List of players who belong to this group.
 */
data class Group(
    val id: Long = 0,
    val name: String,
    val description: String,
    val createdBy: String,
    val createdAt: Long = System.currentTimeMillis(),
    val members: List<Player> = emptyList()
)
