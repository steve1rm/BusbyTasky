package me.androidbox.domain.authentication.usecase.imp

import me.androidbox.domain.authentication.token_repository.TokenRepository
import me.androidbox.domain.authentication.usecase.RetrieveTokenUseCase
import javax.inject.Inject

class RetrieveTokenUseCaseImp @Inject constructor(
    private val tokenRepository: TokenRepository
): RetrieveTokenUseCase {
    override suspend fun execute(key: String): String? {
        return tokenRepository.retrieveUserToken(key)
    }
}