package me.androidbox.data.remote.interceptor

import me.androidbox.domain.authentication.preference.PreferenceRepository
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val preferenceRepository: PreferenceRepository) : Interceptor {

    private companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val loginUser = preferenceRepository.retrieveCurrentUserOrNull()

        return if(loginUser != null) {
            val request = chain.request()
                .newBuilder()
                .addHeader(AUTHORIZATION_HEADER, "Bearer ${loginUser.token}")
                .build()

            chain.proceed(request)
        }
        else {
            chain.proceed(chain.request())
        }
    }
}