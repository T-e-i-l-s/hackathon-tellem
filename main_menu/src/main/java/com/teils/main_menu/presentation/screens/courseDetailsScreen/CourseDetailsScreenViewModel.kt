package com.teils.tellem.main.menu.presentation.screens.courseDetailsScreen

import androidx.lifecycle.ViewModel
import com.teils.main_menu.data.sources.MemorySource
import com.teils.tellem.core.utils.courses.CourseModel
import com.teils.tellem.data_source.data.repositories.CoursesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CourseDetailsScreenViewModel @Inject constructor(
    private val memorySource: MemorySource,
    private val coursesRepository: CoursesRepository
) : ViewModel() {
    private val _course = MutableStateFlow<CourseModel?>(null)
    val course: StateFlow<CourseModel?> = _course

    private val _lessonPassed = MutableStateFlow(false)
    val lessonPassed: StateFlow<Boolean> = _lessonPassed

    private val _flashcardsPassed = MutableStateFlow(false)
    val flashcardsPassed: StateFlow<Boolean> = _flashcardsPassed

    fun loadData(courseId: Int) {
        _lessonPassed.value = memorySource.wasCoursePassed(courseId)
        _flashcardsPassed.value = memorySource.wasFlashcardsPassed(courseId)
        _course.value = coursesRepository.getCourseById(courseId)
    }
}