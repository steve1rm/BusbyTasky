package me.androidbox.data.remote.util

class NetworkHttpException(val errorMessage: String, val errorCode: Int)
    : Exception(errorMessage)
