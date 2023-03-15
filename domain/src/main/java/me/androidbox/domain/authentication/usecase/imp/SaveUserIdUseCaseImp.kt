package me.androidbox.domain.authentication.usecase.imp

import me.androidbox.domain.authentication.preference.PreferenceRepository
import me.androidbox.domain.authentication.usecase.SaveUserIdUseCase
import javax.inject.Inject

class SaveUserIdUseCaseImp @Inject constructor(private val preferenceRepository: PreferenceRepository)
    : SaveUserIdUseCase {

    override fun execute(userId: String) {
        preferenceRepository.saveUserId(userId)
    }
}