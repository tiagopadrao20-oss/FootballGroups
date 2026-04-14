package com.gohan.footballgroups.data.repository

import com.gohan.footballgroups.data.local.dao.GroupDao
import com.gohan.footballgroups.data.mapper.toDomain
import com.gohan.footballgroups.data.mapper.toEntity
import com.gohan.footballgroups.domain.model.Group
import com.gohan.footballgroups.domain.model.Player
import com.gohan.footballgroups.domain.repository.GroupRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

/**
 * Concrete implementation of [GroupRepository] backed by Room.
 *
 * @param groupDao DAO injected by Hilt via [DatabaseModule].
 */
class GroupRepositoryImpl(private val groupDao: GroupDao) : GroupRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getGroups(): Flow<List<Group>> =
        groupDao.getAllGroups().flatMapLatest { entities ->
            // For each group, merge its player list reactively.
            // Using a simple map here; for large datasets consider combining flows.
            kotlinx.coroutines.flow.combine(
                entities.map { entity ->
                    groupDao.getPlayersForGroup(entity.id).map { players ->
                        entity.toDomain(players)
                    }
                }
            ) { it.toList() }
                .let { combined ->
                    if (entities.isEmpty()) {
                        kotlinx.coroutines.flow.flowOf(emptyList())
                    } else {
                        combined
                    }
                }
        }

    override fun getGroupById(groupId: Long): Flow<Group?> =
        groupDao.getGroupById(groupId).map { entity ->
            entity?.let {
                // Grab a one-shot snapshot of players for the detail view.
                // The flatMapLatest pattern is used in getGroups(); here the
                // detail screen collects participants through the events flow.
                it.toDomain()
            }
        }

    override suspend fun createGroup(group: Group): Long =
        groupDao.insertGroup(group.toEntity())

    override suspend fun updateGroup(group: Group) =
        groupDao.updateGroup(group.toEntity())

    override suspend fun deleteGroup(groupId: Long) =
        groupDao.deleteGroup(groupId)

    override suspend fun addMember(player: Player): Long =
        groupDao.insertPlayer(player.toEntity())

    override suspend fun removeMember(playerId: Long) =
        groupDao.deletePlayer(playerId)
}
