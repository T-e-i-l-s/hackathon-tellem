package com.teils.tellem.data_source.data.source.places

import android.content.Context
import com.teils.tellem.data_source.data.source.places.dto.PlaceDto
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PlacesSource @Inject constructor(private val context: Context) {
    companion object {
        const val PLACES_SOURCE_JSON = "places/places.json"
    }

    fun getPlaces(): List<PlaceDto> {
        val jsonString = context.assets.open(PLACES_SOURCE_JSON)
            .bufferedReader()
            .use { it.readText() }

        val json = Json { ignoreUnknownKeys = true }
        val wrapper = json.decodeFromString<List<PlaceDto>>(jsonString)

        return wrapper
    }
}
