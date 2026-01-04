package com.teils.tellem.data_source.data.mappers

import com.teils.tellem.core.utils.places.PlaceModel
import com.teils.tellem.data_source.data.source.places.dto.PlaceDto

fun PlaceDto.toPlaceModel() = PlaceModel(
    name = name,
    imageUrl = imageUrl,
    description = description,
    url = url
)