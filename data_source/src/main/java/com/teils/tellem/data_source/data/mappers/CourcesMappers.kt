package com.teils.tellem.data_source.data.mappers

import com.teils.tellem.core.utils.courses.CourseModel
import com.teils.tellem.core.utils.courses.FlashcardModel
import com.teils.tellem.core.utils.courses.LessonStepModel
import com.teils.tellem.core.utils.courses.PracticeModel
import com.teils.tellem.data_source.data.source.lessons.dto.CourseDto
import com.teils.tellem.data_source.data.source.lessons.dto.FlashcardDto
import com.teils.tellem.data_source.data.source.lessons.dto.LessonStepDto
import com.teils.tellem.data_source.data.source.lessons.dto.PracticeDto

internal fun CourseDto.mapToCourseModel() = CourseModel(
    id = id,
    name = title,
    description = description,
    difficulty = difficulty,
    durationMinutes = durationMinutes,
    lesson = lesson.map { it.mapToLessonStepModel() },
    flashcards = flashcards.map { it.mapToFlashcardModel() },
    practice = practice.map { it.mapToPracticeModel() }
)

private fun LessonStepDto.mapToLessonStepModel() = LessonStepModel(
    step = step,
    phrase = phrase,
    expectedAnswer = expectedAnswer,
    options = options,
    hint = hint
)

private fun FlashcardDto.mapToFlashcardModel() = FlashcardModel(
    front = front,
    back = back
)

private fun PracticeDto.mapToPracticeModel() = PracticeModel(
    name = name,
    description = description,
    url = url
)