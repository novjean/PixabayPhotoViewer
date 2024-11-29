package com.novatech.pixabayphotoviewer.domain.use_case.login

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
class LoginUseCaseTest {

    private lateinit var useCase: LoginUseCase
    private val repository: UserRepository = mockk()

    @Before
    fun setUp() {
        useCase = LoginUseCase(repository)

        // Add a default response for repository.login
        coEvery { repository.login(any(), any()) } returns Result.failure(Exception("Invalid login"))
    }

    @Test
    fun `login succeeds when valid inputs are provided`() = runTest {
        // Mock the repository to return a User object
        val mockUser = User(email = "test@test.com", password = "password123")
        coEvery { repository.login("test@test.com", "password123") } returns Result.success(mockUser)

        // Call the use case
        val result = useCase("test@test.com", "password123")

        // Assert the result
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(mockUser)
    }

    @Test
    fun `login fails when email is empty`() = runTest {
        val result = useCase("", "password123")

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(Exception::class.java)
    }


}
