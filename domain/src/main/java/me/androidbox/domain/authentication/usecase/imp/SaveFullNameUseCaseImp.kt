package me.androidbox.domain.authentication.usecase.imp

import me.androidbox.domain.authentication.preference.PreferenceRepository
import me.androidbox.domain.authentication.usecase.SaveFullNameUseCase
import javax.inject.Inject

class SaveFullNameUseCaseImp @Inject constructor(
    private val preferenceRepository: PreferenceRepository) : SaveFullNameUseCase {

    override suspend fun execute(fullName: String) {
        preferenceRepository.saveFullName(fullName)
    }
}