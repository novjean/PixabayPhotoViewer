package com.novatech.pixabayphotoviewer.domain.use_case.login

import com.google.common.truth.Truth.assertThat
import com.novatech.pixabayphotoviewer.domain.model.User
import com.novatech.pixabayphotoviewer.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginUseCaseTest {

    private lateinit var loginUseCase: LoginUseCase
    private val userRepository: UserRepository = mockk()

    @Before
    fun setUp() {
        loginUseCase = LoginUseCase(userRepository)
    }

    @Test
    fun `login succeeds when valid credentials are provided`() = runTest {
        // Arrange
        val email = "test@test.com"
        val password = "password123"
        val mockUser = User(email = email, password = password)
        coEvery { userRepository.login(email, password) } returns Result.success(mockUser)

        // Act
        val result = loginUseCase(email, password)

        // Assert
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(mockUser)

        // Verify repository interaction
        coVerify(exactly = 1) { userRepository.login(email, password) }
    }

    @Test
    fun `login fails when repository returns failure`() = runTest {
        // Arrange
        val email = "test@test.com"
        val password = "wrongpassword"
        val errorMessage = "Invalid login credentials"
        coEvery { userRepository.login(email, password) } returns Result.failure(Exception(errorMessage))

        // Act
        val result = loginUseCase(email, password)

        // Assert
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo(errorMessage)

        // Verify repository interaction
        coVerify(exactly = 1) { userRepository.login(email, password) }
    }
}
