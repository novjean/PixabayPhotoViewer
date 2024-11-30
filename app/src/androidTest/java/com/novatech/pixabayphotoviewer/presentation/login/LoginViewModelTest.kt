package com.novatech.pixabayphotoviewer.presentation.login

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.Context
import androidx.lifecycle.Observer
import com.novatech.pixabayphotoviewer.LiveDataTestRule
import com.novatech.pixabayphotoviewer.MainDispatcherRule
import com.novatech.pixabayphotoviewer.domain.model.User
import com.novatech.pixabayphotoviewer.domain.use_case.login.LoginUseCaseWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val liveDataTestRule = LiveDataTestRule()

    @Mock
    private lateinit var loginUseCaseWrapper: LoginUseCaseWrapper

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var loginResultObserver: Observer<Result<User>?>

    @Mock
    private lateinit var isLoadingObserver: Observer<Boolean>

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = LoginViewModel(context, loginUseCaseWrapper)
        viewModel.loginResult.observeForever(loginResultObserver)
        viewModel.isLoading.observeForever(isLoadingObserver)
    }

    @Test
    fun loginWithValidCredentialsTriggersSuccess() = runTest {
        val email = "test@test.com"
        val password = "password123"
        val user = User(email, password)
        val result = Result.success(user)

        viewModel.email.postValue(email)
        viewModel.password.postValue(password)

        whenever(loginUseCaseWrapper.execute(email, password)).thenReturn(result)

        viewModel.login()

        verify(isLoadingObserver).onChanged(true)
        verify(loginUseCaseWrapper).execute(email, password)
        verify(loginResultObserver).onChanged(result)
        verify(isLoadingObserver).onChanged(false)
    }

//    @Test
//    fun `login with invalid email sets emailError`() {
//        // Arrange
//        val invalidEmail = "invalid-email"
//        viewModel.email.postValue(invalidEmail)
//        viewModel.password.postValue("password1")
//
//        // Act
//        viewModel.login()
//
//        // Assert
//        assert(viewModel.emailError.value == "Invalid email address")
//    }
//
//    @Test
//    fun `login with invalid password sets passwordError`() {
//        // Arrange
//        val password = "123"
//        viewModel.email.postValue("test@example.com")
//        viewModel.password.postValue(password)
//
//        // Act
//        viewModel.login()
//
//        // Assert
//        assert(viewModel.passwordError.value == "Password must be 6-12 characters")
//    }
//
//    @Test
//    fun `login with empty email and password does not call use case`() {
//        // Arrange
//        viewModel.email.postValue("")
//        viewModel.password.postValue("")
//
//        // Act
//        viewModel.login()
//
//        // Assert
//        verify(loginUseCase, Mockito.never()).invoke(Mockito.anyString(), Mockito.anyString())
//    }
//
//    @Test
//    fun `login use case failure updates loginResult`() = runBlockingTest {
//        // Arrange
//        val email = "test@example.com"
//        val password = "password1"
//        val errorResult = Result.failure<User>(Throwable("Login failed"))
//
//        viewModel.email.postValue(email)
//        viewModel.password.postValue(password)
//
//        whenever(loginUseCase.invoke(email, password)).thenReturn(errorResult)
//
//        // Act
//        viewModel.login()
//
//        // Assert
//        verify(isLoadingObserver).onChanged(true)
//        verify(loginUseCase).invoke(email, password)
//        verify(loginResultObserver).onChanged(errorResult)
//        verify(isLoadingObserver).onChanged(false)
//    }
}
