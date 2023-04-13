package com.example.loginexample.util

interface StateListener {
    fun onLoading()
    fun onSuccess(message:String?)
    fun onError(message: String)

}