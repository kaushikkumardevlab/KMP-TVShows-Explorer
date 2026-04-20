package com.kaushikkumardevlab.kmptvshowsexplorer.core.pagination

import com.kaushikkumardevlab.kmptvshowsexplorer.core.result.Result

class DefaultPaginator<Key, Item>(
    private val initialKey: Key,
    private val onLoadUpdated: (Boolean) -> Unit,
    private val onRequest: suspend (nextKey: Key) -> Result<List<Item>>,
    private val getNextKey: (List<Item>) -> Key,
    private val onError: (String) -> Unit,
    private val onSuccess: (items: List<Item>, newKey: Key) -> Unit
) : Paginator<Key, Item> {

    private var currentKey = initialKey
    private var isMakingRequest = false

    override suspend fun loadNextItems() {

        if (isMakingRequest) return

        isMakingRequest = true
        onLoadUpdated(true)

        val result = onRequest(currentKey)

        isMakingRequest = false
        onLoadUpdated(false)

        when (result) {
            is Result.Success -> {
                val items = result.data
                currentKey = getNextKey(items)
                onSuccess(items, currentKey)
            }
            is Result.Error -> {
                onError(result.message)
            }
            else -> Unit
        }
    }

    override fun reset() {
        currentKey = initialKey
    }
}