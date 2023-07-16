package me.androidbox.domain.login.usecase.imp

import javax.inject.Inject
import me.androidbox.domain.login.CredentialValidator
import me.androidbox.domain.login.usecase.ValidateEmailUseCase

class ValidateEmailUseCaseImp @Inject constructor(private val credentialValidator: CredentialValidator) : ValidateEmailUseCase {
    override fun execute(email: String): Boolean {
        return credentialValidator.validateEmail(email)
    }
}