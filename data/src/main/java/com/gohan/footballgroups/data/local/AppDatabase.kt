package com.gohan.footballgroups.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gohan.footballgroups.data.local.dao.EventDao
import com.gohan.footballgroups.data.local.dao.GroupDao
import com.gohan.footballgroups.data.local.entity.EventParticipantEntity
import com.gohan.footballgroups.data.local.entity.FootballEventEntity
import com.gohan.footballgroups.data.local.entity.GroupEntity
import com.gohan.footballgroups.data.local.entity.PlayerEntity

/**
 * Room database for the Football Groups app.
 *
 * Increment [version] and provide a [androidx.room.migration.Migration] whenever
 * the schema changes in a release build.
 */
@Database(
    entities = [
        GroupEntity::class,
        PlayerEntity::class,
        FootballEventEntity::class,
        EventParticipantEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    /** Returns the DAO for group and player operations. */
    abstract fun groupDao(): GroupDao

    /** Returns the DAO for event and participant operations. */
    abstract fun eventDao(): EventDao

    companion object {
        const val DATABASE_NAME = "football_groups.db"
    }
}
