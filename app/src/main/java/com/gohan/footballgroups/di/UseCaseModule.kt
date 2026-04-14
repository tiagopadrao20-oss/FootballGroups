package com.gohan.footballgroups.di

import com.gohan.footballgroups.domain.repository.EventRepository
import com.gohan.footballgroups.domain.repository.GroupRepository
import com.gohan.footballgroups.domain.usecase.event.CancelParticipationUseCase
import com.gohan.footballgroups.domain.usecase.event.ConfirmParticipationUseCase
import com.gohan.footballgroups.domain.usecase.event.CreateEventUseCase
import com.gohan.footballgroups.domain.usecase.event.GetEventByIdUseCase
import com.gohan.footballgroups.domain.usecase.event.GetEventsForGroupUseCase
import com.gohan.footballgroups.domain.usecase.group.AddMemberUseCase
import com.gohan.footballgroups.domain.usecase.group.CreateGroupUseCase
import com.gohan.footballgroups.domain.usecase.group.DeleteGroupUseCase
import com.gohan.footballgroups.domain.usecase.group.GetGroupByIdUseCase
import com.gohan.footballgroups.domain.usecase.group.GetGroupsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides all domain use cases.
 *
 * Use cases are [Singleton]-scoped to avoid repeated allocations; they hold
 * no mutable state so sharing is safe.
 */
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    // ── Group use cases ──────────────────────────────────────────────────────

    @Provides @Singleton
    fun provideGetGroupsUseCase(r: GroupRepository) = GetGroupsUseCase(r)

    @Provides @Singleton
    fun provideGetGroupByIdUseCase(r: GroupRepository) = GetGroupByIdUseCase(r)

    @Provides @Singleton
    fun provideCreateGroupUseCase(r: GroupRepository) = CreateGroupUseCase(r)

    @Provides @Singleton
    fun provideDeleteGroupUseCase(r: GroupRepository) = DeleteGroupUseCase(r)

    @Provides @Singleton
    fun provideAddMemberUseCase(r: GroupRepository) = AddMemberUseCase(r)

    // ── Event use cases ──────────────────────────────────────────────────────

    @Provides @Singleton
    fun provideGetEventsForGroupUseCase(r: EventRepository) = GetEventsForGroupUseCase(r)

    @Provides @Singleton
    fun provideGetEventByIdUseCase(r: EventRepository) = GetEventByIdUseCase(r)

    @Provides @Singleton
    fun provideCreateEventUseCase(r: EventRepository) = CreateEventUseCase(r)

    @Provides @Singleton
    fun provideConfirmParticipationUseCase(r: EventRepository) = ConfirmParticipationUseCase(r)

    @Provides @Singleton
    fun provideCancelParticipationUseCase(r: EventRepository) = CancelParticipationUseCase(r)
}
