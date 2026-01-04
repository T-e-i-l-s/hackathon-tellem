package com.teils.tellem.data_source.data.repositories

import com.teils.tellem.core.utils.places.PlaceModel
import com.teils.tellem.data_source.data.mappers.toPlaceModel
import com.teils.tellem.data_source.data.source.places.PlacesSource
import javax.inject.Inject

class PlacesRepository @Inject constructor(
    private val placesSource: PlacesSource
) {
    private val places = placesSource.getPlaces()

    fun getPlaces(): List<PlaceModel> {
        return places.map { it.toPlaceModel() }
    }
}