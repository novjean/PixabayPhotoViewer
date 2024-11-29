package com.novatech.pixabayphotoviewer.domain.use_case.register

import com.google.common.truth.Truth.assertThat
import com.novatech.pixabayphotoviewer.domain.model.User
import com.novatech.pixabayphotoviewer.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RegisterUseCaseTest {

    private lateinit var useCase: RegisterUseCase
    private val repository: UserRepository = mockk()

    @Before
    fun setUp() {
        useCase = RegisterUseCase(repository)

        // Add a default response for repository.login
        coEvery { repository.register(any()) } returns Result.failure(Exception("Invalid registration"))
    }

    @Test
    fun `registration succeeds when valid inputs are provided`() = runTest {
        val mockUser = User(email = "test2@test.com", password = "password456", age = 20)
        coEvery { repository.register(mockUser) } returns Result.success(Unit)

        val result = useCase(User("test2@test.com", "password456", 20))

        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `registration fails when age is below 18`() = runTest {
        val result = useCase(User("test@test.com", "password123", 17))

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(Exception::class.java)
    }
}
