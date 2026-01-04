package com.teils.tellem.main.menu.presentation.screens.lessonScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teils.main_menu.data.sources.MemorySource
import com.teils.tellem.core.utils.courses.CourseModel
import com.teils.tellem.data_source.data.repositories.CoursesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LessonScreenViewModel @Inject constructor(
    private val coursesRepository: CoursesRepository,
    private val memorySource: MemorySource,
) : ViewModel() {
    private val _course = MutableStateFlow<CourseModel?>(null)
    val course: StateFlow<CourseModel?> = _course

    private val _currentStep = MutableStateFlow(0)
    val currentStep: StateFlow<Int> = _currentStep

    private val _isAnswerCorrect = MutableStateFlow<Boolean?>(null)
    val isAnswerCorrect: StateFlow<Boolean?> = _isAnswerCorrect

    private val _selectedAnswer = MutableStateFlow<String?>(null)
    val selectedAnswer: StateFlow<String?> = _selectedAnswer

    fun loadData(courseId: Int) {
        _course.value = coursesRepository.getCourseById(courseId)
    }

    fun checkAnswer(answer: String) {
        viewModelScope.launch {
            course.value?.lesson?.let { lessonSafe ->
                _selectedAnswer.value = answer

                delay(500)

                _isAnswerCorrect.value = answer == lessonSafe[currentStep.value].expectedAnswer
            }
        }
    }

    fun nextStep(onLessonCompleted: (passedBefore: Boolean) -> Unit) {
        if (currentStep.value == course.value?.lesson?.size?.let { it - 1 }) {
            course.value?.id?.let { idSafe ->
                onLessonCompleted(memorySource.wasCoursePassed(idSafe))
                memorySource.courseWasPassed(idSafe)
            }
        }
        _currentStep.value = currentStep.value + 1
        _isAnswerCorrect.value = null
        _selectedAnswer.value = null
    }
}