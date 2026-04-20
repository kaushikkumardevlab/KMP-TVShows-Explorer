package com.kaushikkumardevlab.kmptvshowsexplorer

import androidx.compose.ui.window.ComposeUIViewController
import com.kaushikkumardevlab.kmptvshowsexplorer.App
import com.kaushikkumardevlab.kmptvshowsexplorer.data.local.database.DatabaseDriverFactory
import com.kaushikkumardevlab.kmptvshowsexplorer.di.appModule
import org.koin.core.context.startKoin
import org.koin.dsl.module
import platform.UIKit.UIViewController
import kotlin.native.ObjCName
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("KoinHelper")
class KoinHelper {
    fun start() {
        startKoin {
            modules(appModule() + module {
                single { DatabaseDriverFactory() }
            })
        }
    }

    fun createMainViewController(): UIViewController = ComposeUIViewController {
        App()
    }
}
