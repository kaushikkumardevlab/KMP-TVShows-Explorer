package com.kaushikkumardevlab.kmptvshowsexplorer.core.result

suspend inline fun <T> safeApiCall(
    crossinline apiCall: suspend () -> T
): Result<T> {
    return try {
        Result.Success(apiCall())
    } catch (e: Exception) {

        Result.Error(
            message = e.message ?: "Unknown error"
        )
    }
}