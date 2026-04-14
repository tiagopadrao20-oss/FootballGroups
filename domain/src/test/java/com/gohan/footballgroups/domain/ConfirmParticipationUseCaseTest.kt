package com.gohan.footballgroups.domain

import com.gohan.footballgroups.domain.model.EventParticipant
import com.gohan.footballgroups.domain.model.FootballEvent
import com.gohan.footballgroups.domain.repository.EventRepository
import com.gohan.footballgroups.domain.usecase.event.ConfirmParticipationUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ConfirmParticipationUseCaseTest {

    private lateinit var repository: EventRepository
    private lateinit var useCase: ConfirmParticipationUseCase

    private val futureTime = System.currentTimeMillis() + 86_400_000 // +1 day

    @Before
    fun setUp() {
        repository = mockk()
        useCase = ConfirmParticipationUseCase(repository)
    }

    @Test
    fun `invoke confirms participation and returns id`() = runTest {
        val event = buildEvent(maxPlayers = 10)
        coEvery { repository.confirmParticipation(any()) } returns 1L

        val result = useCase(event, "Tiago")

        assertEquals(1L, result)
        coVerify(exactly = 1) { repository.confirmParticipation(any()) }
    }

    @Test(expected = IllegalStateException::class)
    fun `invoke throws when event is full`() = runTest {
        val participants = (1..10).map {
            EventParticipant(eventId = 1, playerName = "Player$it")
        }
        val event = buildEvent(maxPlayers = 10, participants = participants)
        useCase(event, "NewPlayer")
    }

    @Test(expected = IllegalStateException::class)
    fun `invoke throws when player already confirmed`() = runTest {
        val event = buildEvent(
            maxPlayers = 10,
            participants = listOf(EventParticipant(eventId = 1, playerName = "Tiago"))
        )
        useCase(event, "Tiago")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `invoke throws when player name is blank`() = runTest {
        useCase(buildEvent(maxPlayers = 10), "")
    }

    private fun buildEvent(
        maxPlayers: Int,
        participants: List<EventParticipant> = emptyList()
    ) = FootballEvent(
        id = 1,
        groupId = 1,
        title = "Sunday Match",
        dateTime = futureTime,
        location = "Lisbon Park",
        maxPlayers = maxPlayers,
        createdBy = "Admin",
        participants = participants
    )
}
