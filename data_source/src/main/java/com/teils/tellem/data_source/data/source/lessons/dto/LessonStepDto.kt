package com.teils.tellem.data_source.data.source.lessons.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LessonStepDto(
    val step: Int,
    val phrase: String,
    @SerialName("expected_answer")
    val expectedAnswer: String,
    val options: List<String>,
    val hint: String
)