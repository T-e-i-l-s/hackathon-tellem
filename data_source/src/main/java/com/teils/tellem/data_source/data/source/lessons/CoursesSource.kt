package com.teils.tellem.data_source.data.source.lessons

import android.content.Context
import com.teils.tellem.data_source.data.source.lessons.dto.CourseDto
import com.teils.tellem.data_source.data.source.lessons.dto.LessonsListDto
import kotlinx.serialization.json.Json
import javax.inject.Inject

class CoursesSource @Inject constructor(private val context: Context) {
    companion object {
        const val LESSONS_SOURCE_JSON = "lessons/lessons.json"
    }

    fun getLessons(): List<CourseDto> {
        val jsonString = context.assets.open(LESSONS_SOURCE_JSON)
            .bufferedReader()
            .use { it.readText() }

        val json = Json { ignoreUnknownKeys = true }
        val wrapper = json.decodeFromString<LessonsListDto>(jsonString)

        return wrapper.courses
    }
}
