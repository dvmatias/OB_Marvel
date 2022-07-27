package com.cmdv.obmarvel.retrofit

import com.cmdv.obmarvel.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Login interceptor - Retrofit. This class provides logging capabilities for Retrofit service calls actions.
 */
object RetrofitLoggingInterceptor {
    /**
     * Interceptor. Only allows logging on "development" environments.
     */
    val interceptor: HttpLoggingInterceptor =
        HttpLoggingInterceptor().also {
            when (BuildConfig.FLAVOR) {
                "DEV" -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }
}
