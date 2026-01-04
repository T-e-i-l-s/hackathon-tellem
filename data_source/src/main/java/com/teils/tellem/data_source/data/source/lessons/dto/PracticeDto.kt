package com.teils.tellem.data_source.data.source.lessons.dto

import kotlinx.serialization.Serializable

@Serializable
data class PracticeDto(
    val name: String,
    val description: String,
    val url: String
)
