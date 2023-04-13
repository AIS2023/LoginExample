package com.example.loginexample.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.loginexample.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun registerUser(user: User)

    @Query("SELECT * FROM `User Table` WHERE Email=:email AND Password=:password")
    fun loginUser(email: String, password: String): Flow<User?>

    @Query("SELECT * FROM `User Table` WHERE Email=:email")
    fun checkUser(email: String): Flow<User?>

    @Query("UPDATE `User Table` set isUserLogged = 1 WHERE Email=:email")
    suspend fun setUserLoggedIn(email: String)

    @Query("UPDATE `User Table` set isUserLogged = 0 WHERE Email=:email")
    suspend fun setUserLoggedOut(email: String)

    @Query("SELECT * FROM `User Table` WHERE IsUserLogged = 1")
    fun getLoggedInUser(): Flow<User>
}