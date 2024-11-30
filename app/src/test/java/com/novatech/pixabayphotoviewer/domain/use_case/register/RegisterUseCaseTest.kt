package com.novatech.pixabayphotoviewer.domain.use_case.register
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
class RegisterUseCaseTest {

    private lateinit var registerUseCase: RegisterUseCase
    private val userRepository: UserRepository = mockk()

    @Before
    fun setUp() {
        registerUseCase = RegisterUseCase(userRepository)
    }

    @Test
    fun `register succeeds when valid user is provided`() = runTest {
        val validUser = User(email = "newuser@test.com", password = "password123", age = 25)
        coEvery { userRepository.register(validUser) } returns Result.success(Unit)

        val result = registerUseCase(validUser)

        // Assert
        assertThat(result.isSuccess).isTrue()
        coVerify(exactly = 1) { userRepository.register(validUser) }
    }

    @Test
    fun `register fails when repository returns an error`() = runTest {
        val invalidUser = User(email = "newuser@test.com", password = "password123", age = 16)
        val errorMessage = "Age must be 18 or older"
        coEvery { userRepository.register(invalidUser) } returns Result.failure(Exception(errorMessage))

        // Act
        val result = registerUseCase(invalidUser)

        // Assert
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo(errorMessage)
        coVerify(exactly = 1) { userRepository.register(invalidUser) }
    }
}