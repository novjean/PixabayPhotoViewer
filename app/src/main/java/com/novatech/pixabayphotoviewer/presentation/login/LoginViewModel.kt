package com.novatech.pixabayphotoviewer.presentation.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novatech.pixabayphotoviewer.domain.model.User
import com.novatech.pixabayphotoviewer.domain.use_case.login.LoginUseCaseWrapper
import com.novatech.pixabayphotoviewer.util.SecurePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val loginUseCaseWrapper: LoginUseCaseWrapper
) : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()
    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()

    val loginResult = MutableLiveData<Result<User>?>()

    init {
        // Observe email input and clear error if valid
//        email.observeForever { emailValue ->
//            if (isValidEmail(emailValue)) {
//                emailError.value = null
//            }
//        }
//
//        // Observe password input and clear error if valid
//        password.observeForever { passwordValue ->
//            if (isValidPassword(passwordValue)) {
//                passwordError.value = null
//            }
//        }
    }

    fun login() {
        val emailValue = email.value.orEmpty()
        val passwordValue = password.value.orEmpty()
        var isErrorFound = false

        if (!isValidEmail(emailValue)) {
            emailError.value = "Invalid email address"
            isErrorFound = true
        }

        if (!isValidPassword(passwordValue)) {
            passwordError.value = "Password must be 6-12 characters"
            isErrorFound = true
        }

        if(isErrorFound) return

        isLoading.value = true
        emailError.value = null
        passwordError.value = null

        viewModelScope.launch {
            loginResult.value = loginUseCaseWrapper.execute(email.value.orEmpty(), password.value.orEmpty())
            isLoading.value = false

            if(loginResult.value!!.isSuccess){
                SecurePreferences.saveEmail(context, emailValue) // Save email securely
            }

        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length in 6..12
    }

}