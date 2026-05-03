package com.kaushikkumardevlab.kmptvshowsexplorer.data.local.datasource

import com.kaushikkumardevlab.kmptvshowsexplorer.data.local.entity.ShowEntity
import com.kaushikkumardevlab.kmptvshowsexplorer.data.local.entity.ShowDetailEntity
import com.kaushikkumardevlab.kmptvshowsexplorer.data.local.entity.EpisodeEntity
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

    suspend fun getFavoriteShows(): List<ShowEntity> {
        return database.showQueries.getFavoriteShows().executeAsList().map {
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

    suspend fun isFavorite(showId: Int): Boolean {
        return database.showQueries
            .getFavoriteShowById(showId.toLong())
            .executeAsOneOrNull() != null
    }

    suspend fun addFavorite(show: ShowEntity) {
        database.showQueries.insertFavoriteShow(
            id = show.id.toLong(),
            name = show.name,
            genres = show.genres,
            language = show.language,
            imageMedium = show.imageMedium,
            imageOriginal = show.imageOriginal,
            rating = show.rating
        )
    }

    suspend fun removeFavorite(showId: Int) {
        database.showQueries.deleteFavoriteShow(showId.toLong())
    }

    suspend fun getShowDetail(id: Int): ShowDetailEntity? {
        return database.showQueries.getShowDetail(id.toLong()).executeAsOneOrNull()?.let {
            ShowDetailEntity(
                id = it.id.toInt(),
                name = it.name,
                summary = it.summary,
                genres = it.genres,
                language = it.language,
                rating = it.rating,
                imageUrl = it.imageUrl
            )
        }
    }

    suspend fun insertShowDetail(showDetail: ShowDetailEntity) {
        database.showQueries.insertShowDetail(
            id = showDetail.id.toLong(),
            name = showDetail.name,
            summary = showDetail.summary,
            genres = showDetail.genres,
            language = showDetail.language,
            rating = showDetail.rating,
            imageUrl = showDetail.imageUrl
        )
    }

    suspend fun getEpisodes(showId: Int): List<EpisodeEntity> {
        return database.showQueries.getEpisodesByShowId(showId.toLong()).executeAsList().map {
            EpisodeEntity(
                id = it.id.toInt(),
                showId = it.showId.toInt(),
                name = it.name,
                season = it.season.toInt(),
                number = it.number.toInt(),
                summary = it.summary,
                imageUrl = it.imageUrl
            )
        }
    }

    suspend fun insertEpisodes(episodes: List<EpisodeEntity>) {
        episodes.forEach {
            database.showQueries.insertEpisode(
                id = it.id.toLong(),
                showId = it.showId.toLong(),
                name = it.name,
                season = it.season.toLong(),
                number = it.number.toLong(),
                summary = it.summary,
                imageUrl = it.imageUrl
            )
        }
    }
}
