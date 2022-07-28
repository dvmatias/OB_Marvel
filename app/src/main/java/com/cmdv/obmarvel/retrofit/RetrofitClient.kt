package com.cmdv.obmarvel.retrofit

import com.cmdv.obmarvel.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val QUERY_PRAM_API_KEY = "apikey"
    private const val QUERY_PRAM_HASH = "hash"
    private const val QUERY_PRAM_TIMESTAMP = "ts"
    private const val READ_TIMEOUT = 40L
    private const val WRITE_TIMEOUT = 40L
    private const val CONNECTION_TIMEOUT = 40L
    private const val MD5_ALGORITHM = "MD5"

    val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            Interceptor { chain ->
                val original = chain.request()
                val originalHttpUrl: HttpUrl = original.url
                val hash = "${BuildConfig.TIMESTAMP}${BuildConfig.PRIVATE_KEY}${BuildConfig.API_KEY}".md5()
                val url: HttpUrl = originalHttpUrl.newBuilder()
                    .addQueryParameter(QUERY_PRAM_API_KEY, BuildConfig.API_KEY)
                    .addQueryParameter(QUERY_PRAM_HASH, hash)
                    .addQueryParameter(QUERY_PRAM_TIMESTAMP, BuildConfig.TIMESTAMP.toString())
                    .build()

                val requestBuilder: Request.Builder = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .method(original.method, original.body)
                    .url(url)

                val request = requestBuilder.build()
                chain.proceed(request)
            }
        )
        .addInterceptor(RetrofitLoggingInterceptor.interceptor)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        .build()

    private fun String.md5(): String {
        val md = MessageDigest.getInstance(MD5_ALGORITHM)
        return BigInteger(1, md.digest(this.toByteArray())).toString(16).padStart(32, '0')
    }
}