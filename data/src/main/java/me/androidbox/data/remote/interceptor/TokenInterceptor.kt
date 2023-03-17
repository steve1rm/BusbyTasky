package me.androidbox.data.remote.interceptor

import me.androidbox.domain.authentication.model.AuthenticatedUser
import me.androidbox.domain.authentication.preference.PreferenceRepository
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(preferenceRepository: PreferenceRepository) : Interceptor {

    private companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
    }
    private var loginUser: AuthenticatedUser? = null

    init {
        loginUser = preferenceRepository.retrieveCurrentUserOrNull()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(AUTHORIZATION_HEADER, loginUser?.token ?: "")
            .build()

        return chain.proceed(request)
    }
}