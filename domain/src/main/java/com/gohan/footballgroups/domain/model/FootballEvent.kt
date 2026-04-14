package com.gohan.footballgroups.domain.model

/**
 * Represents a scheduled football match event within a [Group].
 *
 * @property id           Unique identifier (0 = not yet persisted).
 * @property groupId      Foreign key to the owning [Group].
 * @property title        Short title for the match event.
 * @property dateTime     Unix epoch milliseconds for the start date/time.
 * @property location     Human-readable address or venue name.
 * @property maxPlayers   Maximum number of players allowed to participate.
 * @property notes        Optional extra information (e.g. bring bibs).
 * @property createdBy    Name of the player who created the event.
 * @property createdAt    Unix epoch milliseconds.
 * @property participants List of confirmed [EventParticipant]s.
 */
data class FootballEvent(
    val id: Long = 0,
    val groupId: Long,
    val title: String,
    val dateTime: Long,
    val location: String,
    val maxPlayers: Int,
    val notes: String = "",
    val createdBy: String,
    val createdAt: Long = System.currentTimeMillis(),
    val participants: List<EventParticipant> = emptyList()
) {
    /** Returns true if the event has not yet occurred. */
    val isUpcoming: Boolean get() = dateTime > System.currentTimeMillis()

    /** Returns the number of free spots remaining. */
    val availableSpots: Int get() = (maxPlayers - participants.size).coerceAtLeast(0)

    /** Returns true when the event is full. */
    val isFull: Boolean get() = participants.size >= maxPlayers
}
