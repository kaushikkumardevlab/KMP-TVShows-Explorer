package com.kaushikkumardevlab.kmptvshowsexplorer.data.local.datasource


import com.kaushikkumardevlab.kmptvshowsexplorer.data.local.entity.ShowEntity
import com.kaushikkumardevlab.kmptvshowsexplorer.data.local.database.ShowDatabase

class ShowLocalDataSource(
    private val database: ShowDatabase
) {

    suspend fun getShows(): List<ShowEntity> {
        return database.showQueries.getShows().executeAsList().map { 
            ShowEntity(
                id = it.id.toInt(),
                name = it.name,
                genres = it.genres,
                language = it.language,
                imageMedium = it.imageMedium,
                imageOriginal = it.imageOriginal,
                rating = it.rating
            )
        }
    }

    suspend fun insertShows(shows: List<ShowEntity>) {
        shows.forEach {
            database.showQueries.insertShow(
                id = it.id.toLong(),
                name = it.name,
                genres = it.genres,
                language = it.language,
                imageMedium = it.imageMedium,
                imageOriginal = it.imageOriginal,
                rating = it.rating
            )
        }
    }

    suspend fun clearShows() {
        database.showQueries.deleteAll()
    }
}