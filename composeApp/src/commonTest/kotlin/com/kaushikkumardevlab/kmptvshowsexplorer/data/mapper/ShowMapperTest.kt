package com.kaushikkumardevlab.kmptvshowsexplorer.data.mapper

import com.kaushikkumardevlab.kmptvshowsexplorer.data.remote.dto.ShowDto
import com.kaushikkumardevlab.kmptvshowsexplorer.data.remote.dto.RatingDto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ShowMapperTest {

    @Test
    fun `maps ShowDto to Show correctly`() {
        val dto = ShowDto(
            id = 1,
            name = "Test",
            genres = listOf("Drama"),
            language = "EN",
            rating = RatingDto(8.0)
        )
        
        val domain = dto.toDomain()
        
        assertEquals(1, domain.id)
        assertEquals("Test", domain.name)
        assertEquals(listOf("Drama"), domain.genres)
        assertEquals("EN", domain.language)
        assertEquals(8.0, domain.rating?.average)
    }

    @Test
    fun `maps ShowDto with nulls to defaults`() {
        val dto = ShowDto(
            id = 1,
            name = "Test",
            genres = null,
            language = null,
            rating = null
        )
        
        val domain = dto.toDomain()
        
        assertTrue(domain.genres.isEmpty())
        assertEquals(null, domain.language)
        assertEquals(null, domain.rating)
    }
}
