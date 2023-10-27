package com.example.mobile_moviescatalog2023.Network

import com.example.mobile_moviescatalog2023.Network.Auth.AuthApi
import com.example.mobile_moviescatalog2023.Network.User.UserApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object Network {
    private val BASE_URL = "https://react-midterm.kreosoft.space/"

    private fun getHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder().apply {
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            retryOnConnectionFailure(false)
            addInterceptor(MyInterceptor())
            val logLevel = HttpLoggingInterceptor.Level.BODY
            addInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
        }
        return client.build()
    }

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(getHttpClient())
            .build()
    }

    private val retrofit: Retrofit = getRetrofit()

    var token: String = ""

    fun getAuthApi(): AuthApi = retrofit.create(AuthApi::class.java)

    fun getUserApi(): UserApi = retrofit.create(UserApi::class.java)
}