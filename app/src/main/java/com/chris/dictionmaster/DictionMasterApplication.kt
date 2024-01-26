package com.chris.dictionmaster

import android.app.Application
import com.chris.dictionmaster.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DictionMasterApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@DictionMasterApplication)
            modules(appModules)
        }
    }
}