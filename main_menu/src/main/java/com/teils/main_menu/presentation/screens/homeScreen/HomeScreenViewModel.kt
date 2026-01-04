package com.teils.main_menu.presentation.screens.homeScreen

import androidx.lifecycle.ViewModel
import com.teils.main_menu.data.sources.XpSource
import com.teils.tellem.core.utils.courses.CourseModel
import com.teils.tellem.data_source.data.repositories.CoursesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val coursesRepository: CoursesRepository,
    private val xpSource: XpSource
) : ViewModel() {
    private val _courses = MutableStateFlow(emptyList<CourseModel>())
    val courses: StateFlow<List<CourseModel>> = _courses

    private val _xp = MutableStateFlow(0)
    val xp: StateFlow<Int> = _xp

    fun loadData() {
        _courses.value = coursesRepository.getAllCourses()
        _xp.value = xpSource.grammar + xpSource.vocabulary + xpSource.communication
    }
}