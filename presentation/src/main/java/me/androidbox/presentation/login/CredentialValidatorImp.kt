package me.androidbox.presentation.login

import android.util.Patterns
import javax.inject.Inject
import me.androidbox.domain.login.CredentialValidator

class CredentialValidatorImp @Inject constructor() : CredentialValidator {

    companion object {
        private val regex = "^(?=.*\\d)(?=.*[A-Z]).{8,}$".toRegex()
    }

    override fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun validatePassword(password: String): Boolean {
        return regex.matches(password)
    }
}