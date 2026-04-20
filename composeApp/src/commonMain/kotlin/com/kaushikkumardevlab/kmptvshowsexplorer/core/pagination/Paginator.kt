package com.kaushikkumardevlab.kmptvshowsexplorer.core.pagination

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}