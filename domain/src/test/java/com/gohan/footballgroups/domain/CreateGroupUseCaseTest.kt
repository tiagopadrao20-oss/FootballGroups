package com.gohan.footballgroups.domain

import com.gohan.footballgroups.domain.model.Group
import com.gohan.footballgroups.domain.repository.GroupRepository
import com.gohan.footballgroups.domain.usecase.group.CreateGroupUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CreateGroupUseCaseTest {

    private lateinit var repository: GroupRepository
    private lateinit var useCase: CreateGroupUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = CreateGroupUseCase(repository)
    }

    @Test
    fun `invoke creates group and returns id`() = runTest {
        val group = Group(name = "Gohan FC", description = "Sunday league", createdBy = "Tiago")
        coEvery { repository.createGroup(group) } returns 1L

        val result = useCase(group)

        assertEquals(1L, result)
        coVerify(exactly = 1) { repository.createGroup(group) }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `invoke throws when name is blank`() = runTest {
        useCase(Group(name = "", description = "desc", createdBy = "Tiago"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `invoke throws when createdBy is blank`() = runTest {
        useCase(Group(name = "Valid Name", description = "desc", createdBy = ""))
    }
}
