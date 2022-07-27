package com.cmdv.obmarvel.retrofit

import com.cmdv.obmarvel.BuildConfig
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val GSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

/**
 * Manager - Retrofit.
 */
class RetrofitManager {
    /**
     * Creates and returns an Retrofit instance.
     */
    fun getInstance(): Retrofit {
        val gson = GsonBuilder()
            .setDateFormat(GSON_DATE_FORMAT)
            .create()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(RetrofitClient.client)
            .build()
    }
}