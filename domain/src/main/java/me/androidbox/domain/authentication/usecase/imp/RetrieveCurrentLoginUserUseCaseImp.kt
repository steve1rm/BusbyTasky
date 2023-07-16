package me.androidbox.domain.authentication.usecase.imp

import javax.inject.Inject
import me.androidbox.domain.authentication.model.AuthenticatedUser
import me.androidbox.domain.authentication.preference.PreferenceRepository
import me.androidbox.domain.authentication.usecase.RetrieveCurrentLoginUserUseCase

class RetrieveCurrentLoginUserUseCaseImp @Inject constructor(
    private val preferenceRepository: PreferenceRepository) : RetrieveCurrentLoginUserUseCase {

    override fun execute(): AuthenticatedUser? {
        return preferenceRepository.retrieveCurrentUserOrNull()
    }
}