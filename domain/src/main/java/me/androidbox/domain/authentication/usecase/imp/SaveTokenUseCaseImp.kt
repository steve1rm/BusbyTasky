package me.androidbox.domain.authentication.usecase.imp

import me.androidbox.domain.authentication.preference.PreferenceRepository
import me.androidbox.domain.authentication.usecase.SaveTokenUseCase
import javax.inject.Inject

class SaveTokenUseCaseImp @Inject constructor(
    private val preferenceRepository: PreferenceRepository) : SaveTokenUseCase {

    override suspend fun execute(token: String) {
        preferenceRepository.saveToken(token)
    }
}