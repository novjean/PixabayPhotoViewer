package com.novatech.pixabayphotoviewer.data.repository

import com.google.common.truth.Truth.assertThat
import com.novatech.pixabayphotoviewer.data.datasource.LocalDataSource
import com.novatech.pixabayphotoviewer.domain.model.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRepositoryImplTest {

    private lateinit var repository: UserRepositoryImpl
    private val localDataSource: LocalDataSource = mockk()

    @Before
    fun setUp() {
        repository = UserRepositoryImpl(localDataSource)
    }

    @Test
    fun `login succeeds when valid credentials are provided`() = runTest {
        val mockUser = User(email = "test@test.com", password = "password123")
        coEvery { localDataSource.mockLogin(mockUser.email, mockUser.password) } returns Result.success(mockUser)

        val result = repository.login(mockUser.email, mockUser.password)

        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(mockUser)

        coVerify(exactly = 1) { localDataSource.mockLogin(mockUser.email, mockUser.password) }
    }

    @Test
    fun `login fails when invalid credentials are provided`() = runTest {
        val invalidEmail = "invalid@test.com"
        val invalidPassword = "wrongpassword"
        coEvery { localDataSource.mockLogin(invalidEmail, invalidPassword) } returns Result.failure(Exception("Invalid login credentials"))

        // Act
        val result = repository.login(invalidEmail, invalidPassword)

        // Assert
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo("Invalid login credentials")
        coVerify(exactly = 1) { localDataSource.mockLogin(invalidEmail, invalidPassword) }
    }

    @Test
    fun `register succeeds when age is valid`() = runTest {
        val validUser = User(email = "newuser@test.com", password = "newpassword", age = 25)
        coEvery { localDataSource.mockRegister(validUser) } returns Result.success(Unit)

        // Act
        val result = repository.register(validUser)

        // Assert
        assertThat(result.isSuccess).isTrue()
        coVerify(exactly = 1) { localDataSource.mockRegister(validUser) }
    }

    @Test
    fun `register fails when age is invalid`() = runTest {
        val invalidUser = User(email = "newuser@test.com", password = "newpassword", age = 16)
        coEvery { localDataSource.mockRegister(invalidUser) } returns Result.failure(Exception("Age must be between 18 and 99"))

        // Act
        val result = repository.register(invalidUser)

        // Assert
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo("Age must be between 18 and 99")
        coVerify(exactly = 1) { localDataSource.mockRegister(invalidUser) }
    }
}
