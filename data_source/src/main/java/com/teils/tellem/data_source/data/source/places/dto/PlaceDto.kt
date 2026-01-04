package com.teils.tellem.data_source.data.source.places.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlaceDto(
    val name: String,
    val description: String,
    val imageUrl: String,
    val url: String
)