package com.teils.tellem.core.utils.courses

import androidx.annotation.IntRange

data class CourseModel(
    var id: Int,
    val name: String,
    val description: String,
    @param:IntRange(1, 5) val difficulty: Int,
    val durationMinutes: Int,
    val lesson: List<LessonStepModel>,
    val flashcards: List<FlashcardModel>,
    val practice: List<PracticeModel>
)