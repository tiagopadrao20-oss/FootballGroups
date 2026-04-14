package com.gohan.footballgroups.domain.model

/**
 * Records a player's confirmation of participation in a [FootballEvent].
 *
 * @property id          Unique identifier (0 = not yet persisted).
 * @property eventId     Foreign key to the [FootballEvent].
 * @property playerName  Name of the confirming player.
 * @property confirmedAt Unix epoch milliseconds.
 */
data class EventParticipant(
    val id: Long = 0,
    val eventId: Long,
    val playerName: String,
    val confirmedAt: Long = System.currentTimeMillis()
)
