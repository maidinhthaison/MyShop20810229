package com.ql2.myshop.data.retrofit

import com.google.gson.Gson
import com.ql2.myshop.BuildConfig
import com.ql2.myshop.config.AppConfig
import com.ql2.myshop.config.BackendEnvironment
import com.ql2.myshop.data.network.ConnectivityDataSource
import com.ql2.myshop.data.retrofit.interceptors.HttpLoggingInterceptor
import com.ql2.myshop.domain.local.ConfigServer
import com.ql2.myshop.domain.model.config.ConfigModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitManager (
    private val gson: Gson,
    private val connectivityDataSource: ConnectivityDataSource,
    private val configServer: ConfigServer
) {

    class NetworkLogger : HttpLoggingInterceptor.Logger {
        override fun log(message: String?) {
            Timber.d(message)
        }
    }

    private fun createHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addNetworkInterceptor { chain ->
                // validate network connection
                if (!connectivityDataSource.connected) {
                    throw UnknownHostException()
                }

                val newRequestBuilder = chain.request().newBuilder()
                newRequestBuilder.addHeader("accept", "application/json")
                val newRequest = newRequestBuilder.build()
                return@addNetworkInterceptor chain.proceed(newRequest)
            }
            /*if (BuildConfig.DEBUG) {
                if (AppConfig.backendEnvironment == BackendEnvironment.Dev
                    || AppConfig.backendEnvironment == BackendEnvironment.Stag) {
                    addNetworkInterceptor(HttpLoggingInterceptor(NetworkLogger()).apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            }*/
            if (BuildConfig.DEBUG) {
                addNetworkInterceptor(HttpLoggingInterceptor(NetworkLogger()).apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }

        }.connectTimeout(90, TimeUnit.SECONDS).callTimeout(90, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS).build()
    }

    private fun createRetrofit(): Retrofit {
        val configModel = configServer.getConfig()
        if(configModel?.server.isNullOrEmpty() || configModel?.port.isNullOrEmpty()){
            return Retrofit.Builder().baseUrl(BuildConfig.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).client(createHttpClient())
                .build()
        }
        val baseUrl = configServer.getServer(configModel)
        Timber.d(">>>>${baseUrl}")
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson)).client(createHttpClient())
            .build()
    }

    operator fun <T> get(apiServiceClazz: Class<T>): T {
        return createRetrofit().create(apiServiceClazz)
    }
}
