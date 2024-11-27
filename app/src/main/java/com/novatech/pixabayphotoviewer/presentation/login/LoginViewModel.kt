package com.novatech.pixabayphotoviewer.presentation.login

import androidx.lifecycle.*
import com.novatech.pixabayphotoviewer.domain.model.User
import com.novatech.pixabayphotoviewer.domain.use_case.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val loginResult = MutableLiveData<Result<User>?>()

    fun login() {
        viewModelScope.launch {
            loginResult.value = loginUseCase(email.value.orEmpty(), password.value.orEmpty())
        }
    }
}