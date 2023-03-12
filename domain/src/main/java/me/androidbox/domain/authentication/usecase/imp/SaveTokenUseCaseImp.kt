package me.androidbox.domain.authentication.usecase.imp

import me.androidbox.domain.authentication.preference.PreferenceRepository
import me.androidbox.domain.authentication.usecase.SaveTokenUseCase
import javax.inject.Inject

class SaveTokenUseCaseImp @Inject constructor(
    private val preferenceRepository: PreferenceRepository) : SaveTokenUseCase {
    override suspend fun execute(key: String, token: String) {
        preferenceRepository.saveToken(key, token)
    }
}