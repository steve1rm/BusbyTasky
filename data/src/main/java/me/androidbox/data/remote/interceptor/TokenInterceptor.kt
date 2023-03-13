package me.androidbox.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {

    private companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(AUTHORIZATION_HEADER, "")
            .build()

        return chain.proceed(request)
    }
}