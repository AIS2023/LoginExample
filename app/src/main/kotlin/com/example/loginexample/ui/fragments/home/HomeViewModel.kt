package com.example.loginexample.ui.fragments.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginexample.models.User
import com.example.loginexample.repository.AuthRepository
import com.example.loginexample.util.StateListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val authRepository: AuthRepository
    ) :
    ViewModel() {

    var stateListener: StateListener? = null

    private val _loggedInUser = MutableLiveData<User>()
    val loggedInUser = _loggedInUser

    init {
        getLoggedInUser()
    }

    private fun getLoggedInUser() {
        stateListener?.onLoading()

        viewModelScope.launch {
            try {
                val user_response = authRepository.getLoggedInUser()            // The variable "user_response" should be written in the camel case style
                user_response.collect { user ->
                    _loggedInUser.value = user
                }

                stateListener?.onSuccess("Fetched logged in user")
                return@launch
            } catch (e: Exception) {
                stateListener?.onError(e.message!!)
                return@launch
            }
        }
    }
}
