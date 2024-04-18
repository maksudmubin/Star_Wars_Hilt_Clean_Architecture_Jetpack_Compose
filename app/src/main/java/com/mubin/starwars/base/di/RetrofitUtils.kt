package com.mubin.starwars.base.di

import android.content.Context
import com.google.gson.Gson
import com.itkacher.okprofiler.BuildConfig
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.mubin.starwars.base.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
object RetrofitUtils {
    private fun createCache(application: Context): Cache {
        val cacheSize = 5L * 1024L * 1024L //10MB
        return Cache(File(application.cacheDir, "${application.packageName}.cache"), cacheSize)
    }

    private fun createOkHttpClient(cache: Cache?): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(OkHttpProfilerInterceptor())
            }
            cache(cache)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(1, TimeUnit.MINUTES)
            connectTimeout(30, TimeUnit.SECONDS)
        }.build()
    }

    @Provides
    fun retrofitInstance(@ApplicationContext context: Context): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .baseUrl(Constants.BASE_URL)
            .client(createOkHttpClient(createCache(context)))
            .build()
    }

}