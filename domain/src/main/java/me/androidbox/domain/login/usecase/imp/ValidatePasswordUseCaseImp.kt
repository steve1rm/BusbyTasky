package me.androidbox.domain.login.usecase.imp

import me.androidbox.domain.login.CredentialValidator
import me.androidbox.domain.login.usecase.ValidatePasswordUseCase
import javax.inject.Inject

class ValidatePasswordUseCaseImp @Inject constructor(private val credentialValidator: CredentialValidator): ValidatePasswordUseCase {
    override fun execute(password: String): Boolean {
        return credentialValidator.validatePassword(password)
    }
}
