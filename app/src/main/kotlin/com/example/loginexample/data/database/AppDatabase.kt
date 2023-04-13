package com.example.loginexample.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.loginexample.data.dao.UserDao
import com.example.loginexample.models.User

@Database(entities = [User::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}