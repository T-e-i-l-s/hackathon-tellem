package com.teils.tellem.data_source.data.source.lessons.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseDto(
    val id: Int,
    val title: String,
    val description: String,
    val difficulty: Int,
    @SerialName("duration_minutes") val durationMinutes: Int,
    val lesson: List<LessonStepDto>,
    val flashcards: List<FlashcardDto>,
    val practice: List<PracticeDto>
)
