package me.androidbox.domain.authentication.usecase.imp

import javax.inject.Inject
import me.androidbox.domain.authentication.preference.PreferenceRepository
import me.androidbox.domain.authentication.usecase.DeleteCurrentUserUseCase

class DeleteCurrentUserUseCaseImp @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) : DeleteCurrentUserUseCase {

    override fun execute() {
        preferenceRepository.deleteCurrentUser()
    }
}
