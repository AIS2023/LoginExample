package com.example.loginexample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LoginExample : Application(){

    override fun onCreate() {
        super.onCreate()
    }
}