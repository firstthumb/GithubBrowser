package com.ekocaman.app.githubbrowser.di.modules

import com.ekocaman.app.githubbrowser.BuildConfig
import com.ekocaman.app.githubbrowser.data.api.GithubApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val client = OkHttpClient().newBuilder()
        client.addInterceptor { it ->
            val original = it.request()
            val builder = original.newBuilder()
            BuildConfig.GITHUB_API_KEY.let { key ->
                builder.addHeader("Authorization", "token $key")
            }
            val request = builder.method(original.method(), original.body())
                    .build()
            it.proceed(request)
        }
        if (BuildConfig.DEBUG) {
            client.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return client.build()
    }


    @Provides
    @Singleton
    fun provideHomeApi(client: OkHttpClient): GithubApi {
        return createApiClient(GithubApi::class.java,
                "https://api.github.com",
                client)
    }

    private fun <T> createApiClient(clazz: Class<T>, baseUrl: String, client: OkHttpClient): T {
        return Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(clazz)
    }
}