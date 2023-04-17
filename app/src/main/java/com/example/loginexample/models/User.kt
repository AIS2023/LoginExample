package com.example.loginexample.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User Table")
data class User(

    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "First_Name")
    val firstName: String?,

    @ColumnInfo(name = "Last_Name")
    val lastName: String?,

    @ColumnInfo(name = "Email")
    val email: String,

    @ColumnInfo(name = "Phone_Number")
    val phoneNumber: String,

    @ColumnInfo(name = "Password")
    val password: String,

    @ColumnInfo(name = "IsUserLogged")
    val isUserLogged: Boolean
)
