package com.project.doctors

import android.app.Application
import com.project.doctors.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DoctorApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@DoctorApplication)
            modules(appModule)
        }
    }

}