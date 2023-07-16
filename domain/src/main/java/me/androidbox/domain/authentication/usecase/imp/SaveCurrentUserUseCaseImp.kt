package me.androidbox.domain.authentication.usecase.imp

import javax.inject.Inject
import me.androidbox.domain.authentication.model.AuthenticatedUser
import me.androidbox.domain.authentication.preference.PreferenceRepository
import me.androidbox.domain.authentication.usecase.SaveCurrentUserUseCase

class SaveCurrentUserUseCaseImp @Inject constructor(
    private val preferenceRepository: PreferenceRepository) : SaveCurrentUserUseCase {

    override fun execute(authenticatedUser: AuthenticatedUser) {
        preferenceRepository.saveCurrentUser(authenticatedUser)
    }
}