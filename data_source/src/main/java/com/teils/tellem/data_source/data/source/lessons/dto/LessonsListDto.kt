package com.teils.tellem.data_source.data.source.lessons.dto

import kotlinx.serialization.Serializable

@Serializable
data class LessonsListDto(
    val courses: List<CourseDto>
)