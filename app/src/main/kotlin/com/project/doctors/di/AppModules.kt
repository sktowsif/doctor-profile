package com.project.doctors.di

import com.project.doctors.BuildConfig
import com.project.doctors.data.mapper.RecordListMapper
import com.project.doctors.data.mapper.RecordMapper
import com.project.doctors.data.source.RecordAPI
import com.project.doctors.data.vm.RecordViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    single { RecordMapper() }
    single { RecordListMapper(get()) }

    single { createWebService<RecordAPI>(BuildConfig.BASE_API_URL, true) }

    viewModel { RecordViewModel(get(), get()) }
}

inline fun <reified T> createWebService(url: String, gsonEnable: Boolean = true): T {

    val okHttpBuilder = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)

    if (BuildConfig.DEBUG) {
        val logger = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpBuilder.addInterceptor(logger)
    }

    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpBuilder.build())

    if (gsonEnable) retrofit.addConverterFactory(GsonConverterFactory.create())
    return retrofit.build().create(T::class.java)
}