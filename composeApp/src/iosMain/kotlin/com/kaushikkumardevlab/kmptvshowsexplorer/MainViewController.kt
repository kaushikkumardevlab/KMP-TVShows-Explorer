package com.kaushikkumardevlab.kmptvshowsexplorer

import androidx.compose.ui.window.ComposeUIViewController
import com.kaushikkumardevlab.kmptvshowsexplorer.data.local.database.DatabaseDriverFactory
import com.kaushikkumardevlab.kmptvshowsexplorer.di.appModule
import org.koin.core.context.startKoin
import org.koin.dsl.module
import platform.UIKit.UIViewController
import kotlin.native.ObjCName
import kotlin.experimental.ExperimentalObjCName

//OptIn@OptIn(ExperimentalObjCName::class)
//@ObjCName("createMainViewController")
//fun createMainViewController(): UIViewController = ComposeUIViewController {
//    App()
//}
@OptIn(ExperimentalObjCName::class)
@ObjCName("MainViewController")
class MainViewController {
    fun createMainViewController(): UIViewController = ComposeUIViewController {
        App()
    }
}
