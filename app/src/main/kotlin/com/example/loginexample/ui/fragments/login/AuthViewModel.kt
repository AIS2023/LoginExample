package com.example.loginexample.ui.fragments.login

import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.loginexample.models.User
import com.example.loginexample.repository.AuthRepository
import com.example.loginexample.util.StateListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/* add comments for all the methods in this class*/
class AuthViewModel @ViewModelInject constructor(private val authRepository: AuthRepository) :
    ViewModel(), Observable {
    /* use camel casing for the variable state_listener */
    var state_listener: StateListener? = null

    @Bindable
    val firstName = MutableLiveData<String>()

    @Bindable
    val lastName = MutableLiveData<String>()

    @Bindable
    val emailAddress = MutableLiveData<String>()

    @Bindable
    val phoneNumber = MutableLiveData<String>()

    @Bindable
    val password = MutableLiveData<String>()

    fun loginUser(view: View) {
        state_listener?.onLoading()

        if (emailAddress.value.isNullOrEmpty()) {
            state_listener?.onError("Enter email address") /* define constants for validation messages */
            return
        } else if (password.value.isNullOrEmpty()) {
            state_listener?.onError("Enter password") /* define constants for validation messages */
            return
        }


        viewModelScope.launch {
            try {
                /* use camel casing for the variable is_user_exist */
                val is_user_exist = authRepository.checkUser(emailAddress.value!!)
                is_user_exist.collect { user ->
                    when {
                        (emailAddress.value !=
                                user?.email) -> {
                            state_listener?.onError("User account not found") /* define constants for validation messages */
                            return@collect
                        }
                        else -> {
                            val loginResponse =
                                authRepository.loginUser(emailAddress.value!!, password.value!!)

                            loginResponse.collect { user ->
                                when {
                                    (password.value != user?.password) -> {
                                        state_listener?.onError("Incorrect password") /* define constants for validation messages */
                                        return@collect
                                    }
                                    else -> {
                                        state_listener?.onSuccess("Welcome, ${user?.firstName} ${user?.lastName}")

                                        authRepository.setUserLoggedIn(emailAddress.value!!)

                                        return@collect
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                state_listener?.onError(e.message!!)
                return@launch
            }
        }
    }

    fun registerUser(view: View) {
        when {
            firstName.value.isNullOrEmpty() -> {
                state_listener?.onError("Enter first name")
                return
            }
            lastName.value.isNullOrEmpty() -> {
                state_listener?.onError("Enter last name")
                return
            }
            emailAddress.value.isNullOrEmpty() -> {
                state_listener?.onError("Enter email address")
                return
            }
            phoneNumber.value.isNullOrEmpty() -> {
                state_listener?.onError("Enter phone number")
                return
            }
            password.value.isNullOrEmpty() -> {
                state_listener?.onError("Enter password")
                return
            }
        }

        viewModelScope.launch {
            try {
                val user = User(
                    null,
                    firstName.value!!,
                    lastName.value!!,
                    emailAddress.value!!,
                    phoneNumber.value!!,
                    password.value!!,
                    isUserLogged = false
                )

                authRepository.registerUser(user)

                state_listener?.onSuccess("Welcome, ${firstName.value} ${lastName.value}")

                authRepository.setUserLoggedIn(emailAddress.value!!)

                return@launch
            } catch (e: Exception) {
                state_listener?.onError(e.message!!)
                return@launch
            }
        }

    }

    private val loggedInUser = MutableLiveData<User>()

    fun isUserLoggedIn() = liveData {
        try {
            val isUserLoggedIn = authRepository.getLoggedInUser()
            isUserLoggedIn.collect {user ->
                loggedInUser.value = user
                if (user.isUserLogged) {
                    emit(true)
                }
            }
        } catch  (e: Exception) {
            emit(false)
        }
        return@liveData
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}
