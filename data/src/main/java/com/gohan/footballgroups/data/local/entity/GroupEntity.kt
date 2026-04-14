package com.gohan.footballgroups.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity that maps to the `groups` table.
 */
@Entity(tableName = "groups")
data class GroupEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val createdBy: String,
    val createdAt: Long
)
