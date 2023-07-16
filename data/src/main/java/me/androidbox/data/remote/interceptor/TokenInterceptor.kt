package me.androidbox.data.remote.interceptor

import me.androidbox.domain.authentication.preference.PreferenceRepository
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val preferenceRepository: PreferenceRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val loginUser = preferenceRepository.retrieveCurrentUserOrNull()

        return if (loginUser == null) {
            chain.proceed(chain.request())
        } else {
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer ${loginUser.token}")
                .build()

            chain.proceed(request)
        }
    }
}