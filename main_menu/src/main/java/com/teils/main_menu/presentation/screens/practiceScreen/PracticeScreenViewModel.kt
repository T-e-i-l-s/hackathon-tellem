package com.teils.tellem.main.menu.presentation.screens.practiceScreen

import androidx.lifecycle.ViewModel
import com.teils.tellem.core.utils.courses.CourseModel
import com.teils.tellem.data_source.data.repositories.CoursesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PracticeScreenViewModel @Inject constructor(
    private val coursesRepository: CoursesRepository
) : ViewModel() {
    private val _course = MutableStateFlow<CourseModel?>(null)
    val course: StateFlow<CourseModel?> = _course

    fun loadData(courseId: Int) {
        _course.value = coursesRepository.getCourseById(courseId)
    }
}