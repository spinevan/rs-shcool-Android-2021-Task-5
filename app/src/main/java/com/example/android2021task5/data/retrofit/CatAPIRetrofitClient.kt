package com.example.android2021task5.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object CatAPIRetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

}