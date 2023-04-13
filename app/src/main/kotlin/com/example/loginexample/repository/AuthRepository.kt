package com.example.loginexample.repository

import com.example.loginexample.data.database.AppDatabase
import com.example.loginexample.models.User
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val appDatabase: AppDatabase
) {

    fun getLoggedInUser() = appDatabase.userDao().getLoggedInUser()

    fun loginUser(email: String, password: String) = appDatabase.userDao().loginUser(email, password)

    fun checkUser(email: String) = appDatabase.userDao().checkUser(email)

    suspend fun registerUser(user: User) = appDatabase.userDao().registerUser(user)

    suspend fun setUserLoggedIn(email: String) = appDatabase.userDao().setUserLoggedIn(email)

    suspend fun setUserLoggedOut(email: String) = appDatabase.userDao().setUserLoggedOut(email)
}