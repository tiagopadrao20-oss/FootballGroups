package com.gohan.footballgroups.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Room entity that maps to the `event_participants` table.
 * Cascades deletion when the parent event is deleted.
 */
@Entity(
    tableName = "event_participants",
    foreignKeys = [
        ForeignKey(
            entity = FootballEventEntity::class,
            parentColumns = ["id"],
            childColumns = ["eventId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("eventId")]
)
data class EventParticipantEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val eventId: Long,
    val playerName: String,
    val confirmedAt: Long
)
