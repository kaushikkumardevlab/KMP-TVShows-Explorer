package com.kaushikkumardevlab.kmptvshowsexplorer.core.logger

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

object Logger {
    fun init() {
        Napier.base(DebugAntilog())
    }
    fun d(message: String) {
        Napier.d(message)
    }
    fun e(message: String) {
        Napier.e(message)
    }
}