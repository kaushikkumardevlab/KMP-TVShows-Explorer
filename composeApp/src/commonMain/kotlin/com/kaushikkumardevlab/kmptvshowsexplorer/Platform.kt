package com.kaushikkumardevlab.kmptvshowsexplorer

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform