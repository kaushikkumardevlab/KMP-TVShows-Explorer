package com.kaushikkumardevlab.kmptvshowsexplorer

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}