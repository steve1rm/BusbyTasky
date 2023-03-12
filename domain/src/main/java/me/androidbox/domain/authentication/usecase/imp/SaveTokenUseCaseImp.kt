package me.androidbox.domain.authentication.usecase.imp

import me.androidbox.domain.authentication.token_repository.TokenRepository
import me.androidbox.domain.authentication.usecase.SaveTokenUseCase
import javax.inject.Inject

class SaveTokenUseCaseImp @Inject constructor(
    private val tokenRepository: TokenRepository) : SaveTokenUseCase {
    override suspend fun execute(key: String, token: String) {
        tokenRepository.saveUserToken(key, token)
    }
}