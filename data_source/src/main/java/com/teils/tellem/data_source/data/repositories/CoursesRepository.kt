package com.teils.tellem.data_source.data.repositories

import com.teils.tellem.core.utils.courses.CourseModel
import com.teils.tellem.data_source.data.mappers.mapToCourseModel
import com.teils.tellem.data_source.data.source.lessons.CoursesSource
import javax.inject.Inject

class CoursesRepository @Inject constructor(
    coursesSource: CoursesSource
) {
    private val courses = coursesSource.getLessons().map { it.mapToCourseModel() }

    fun getAllCourses(): List<CourseModel> {
        return courses
    }

    fun getCourseById(id: Int): CourseModel {
        return courses.find { it.id == id } ?: courses[0]
    }
}