package me.androidbox.domain.authentication.usecase.imp

import me.androidbox.domain.authentication.preference.PreferenceRepository
import me.androidbox.domain.authentication.usecase.DeleteCurrentUserUseCase
import javax.inject.Inject

class DeleteCurrentUserUseCaseImp @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) : DeleteCurrentUserUseCase {

    override fun execute() {
        preferenceRepository.deleteCurrentUser()
    }
}
