package me.androidbox.domain.authentication.usecase.imp

import me.androidbox.domain.authentication.model.LoginUser
import me.androidbox.domain.authentication.preference.PreferenceRepository
import me.androidbox.domain.authentication.usecase.SaveCurrentUserUseCase
import javax.inject.Inject

class SaveCurrentUserUseCaseImp @Inject constructor(
    private val preferenceRepository: PreferenceRepository) : SaveCurrentUserUseCase {

    override fun execute(loginUser: LoginUser) {
        preferenceRepository.saveCurrentUser(loginUser)
    }
}