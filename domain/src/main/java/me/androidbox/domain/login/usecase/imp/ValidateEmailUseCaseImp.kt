package me.androidbox.domain.login.usecase.imp

import me.androidbox.domain.login.CredentialValidator
import me.androidbox.domain.login.usecase.ValidateEmailUseCase
import javax.inject.Inject

class ValidateEmailUseCaseImp @Inject constructor(private val credentialValidator: CredentialValidator) : ValidateEmailUseCase {
    override fun execute(email: String): Boolean {
        return credentialValidator.validateEmail(email)
    }
}