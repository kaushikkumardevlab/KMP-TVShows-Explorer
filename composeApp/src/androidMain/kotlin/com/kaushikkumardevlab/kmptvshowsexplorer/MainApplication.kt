package com.kaushikkumardevlab.kmptvshowsexplorer

import android.app.Application
import com.kaushikkumardevlab.kmptvshowsexplorer.data.local.database.DatabaseDriverFactory
import com.kaushikkumardevlab.kmptvshowsexplorer.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(appModule() + module {
                single { DatabaseDriverFactory(this@MainApplication) }
            })
        }
    }
}
