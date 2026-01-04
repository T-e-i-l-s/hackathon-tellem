package com.teils.tellem.core.utils.courses

data class LessonStepModel(
    val step: Int,
    val phrase: String,
    val expectedAnswer: String,
    val options: List<String>,
    val hint: String
)