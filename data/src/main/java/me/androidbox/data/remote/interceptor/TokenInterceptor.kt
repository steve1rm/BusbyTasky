package me.androidbox.data.remote.interceptor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.androidbox.domain.authentication.model.LoginUser
import me.androidbox.domain.authentication.preference.PreferenceRepository
import okhttp3.Interceptor
import okhttp3.Response
import kotlin.coroutines.EmptyCoroutineContext

class TokenInterceptor(preferenceRepository: PreferenceRepository) : Interceptor {

    private companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
    }
    private var loginUser: LoginUser? = null

    init {
        CoroutineScope(EmptyCoroutineContext).launch {
            loginUser = preferenceRepository.retrieveCurrentUserOrNull()
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(AUTHORIZATION_HEADER, loginUser?.token ?: "")
            .build()

        return chain.proceed(request)
    }
}