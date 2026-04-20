package com.kaushikkumardevlab.kmptvshowsexplorer.data.local.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.kaushikkumardevlab.kmptvshowsexplorer.data.local.database.ShowDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(ShowDatabase.Schema, "show.db")
    }
}
