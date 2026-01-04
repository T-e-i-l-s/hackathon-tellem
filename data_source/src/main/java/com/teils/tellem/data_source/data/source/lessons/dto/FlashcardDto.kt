package com.teils.tellem.data_source.data.source.lessons.dto

import kotlinx.serialization.Serializable

@Serializable
data class FlashcardDto(
    val front: String,
    val back: String
)