package com.gohan.footballgroups.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Room entity that maps to the `events` table.
 * Cascades deletion when the parent group is deleted.
 */
@Entity(
    tableName = "events",
    foreignKeys = [
        ForeignKey(
            entity = GroupEntity::class,
            parentColumns = ["id"],
            childColumns = ["groupId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("groupId")]
)
data class FootballEventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val groupId: Long,
    val title: String,
    val dateTime: Long,
    val location: String,
    val maxPlayers: Int,
    val notes: String,
    val createdBy: String,
    val createdAt: Long
)
