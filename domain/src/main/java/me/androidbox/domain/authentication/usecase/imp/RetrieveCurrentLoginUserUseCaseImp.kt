package me.androidbox.domain.authentication.usecase.imp

import me.androidbox.domain.authentication.model.AuthenticatedUser
import me.androidbox.domain.authentication.preference.PreferenceRepository
import me.androidbox.domain.authentication.usecase.RetrieveCurrentLoginUserUseCase
import javax.inject.Inject

class RetrieveCurrentLoginUserUseCaseImp @Inject constructor(
    private val preferenceRepository: PreferenceRepository) : RetrieveCurrentLoginUserUseCase {

    override fun execute(): AuthenticatedUser? {
        return preferenceRepository.retrieveCurrentUserOrNull()
    }
}