package me.androidbox.domain.login

interface CredentialValidator {
    fun validateEmail(email: String): Boolean
    fun validatePassword(password: String): Boolean
}