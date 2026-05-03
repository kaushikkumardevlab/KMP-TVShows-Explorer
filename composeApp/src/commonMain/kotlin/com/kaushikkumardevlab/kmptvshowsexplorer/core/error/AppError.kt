package com.kaushikkumardevlab.kmptvshowsexplorer.core.error

import io.ktor.utils.io.errors.IOException

sealed class AppError {
    object Network : AppError()
    object NotFound : AppError()
    object Unknown : AppError()
}

fun Throwable.toAppError(): AppError {
    return when (this) {
        is IOException -> AppError.Network
        else -> AppError.Unknown
    }
}