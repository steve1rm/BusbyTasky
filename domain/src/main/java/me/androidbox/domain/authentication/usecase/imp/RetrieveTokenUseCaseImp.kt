package me.androidbox.domain.authentication.usecase.imp

import me.androidbox.domain.authentication.preference.PreferenceRepository
import me.androidbox.domain.authentication.usecase.RetrieveTokenUseCase
import javax.inject.Inject

class RetrieveTokenUseCaseImp @Inject constructor(
    private val preferenceRepository: PreferenceRepository
): RetrieveTokenUseCase {
    override suspend fun execute(key: String): String? {
        return preferenceRepository.saveToken()
    }
}