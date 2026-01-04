package com.teils.tellem.main.menu.presentation.screens.flashcards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teils.main_menu.data.sources.MemorySource
import com.teils.tellem.core.utils.courses.CourseModel
import com.teils.tellem.core.utils.courses.FlashcardModel
import com.teils.tellem.data_source.data.repositories.CoursesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlashcardsScreenViewModel @Inject constructor(
    private val coursesRepository: CoursesRepository,
    private val memorySource: MemorySource
) : ViewModel() {
    private val _course = MutableStateFlow<CourseModel?>(null)
    val course: StateFlow<CourseModel?> = _course

    private val _flashcards = MutableStateFlow<List<FlashcardModel>?>(null)
    val flashcards: StateFlow<List<FlashcardModel>?> = _flashcards

    fun loadData(courseId: Int) {
        val course = coursesRepository.getCourseById(courseId)
        _course.value = course
        _flashcards.value = course.flashcards
    }

    fun rememberCard(onCompleted: (passedBefore: Boolean) -> Unit) {
        viewModelScope.launch {
            val current = _flashcards.value ?: return@launch
            if (current.isNotEmpty()) {
                _flashcards.value = current.drop(1)
                delay(30)
            }
            if (flashcards.value?.isEmpty() == true) {
                course.value?.id?.let { idSafe ->
                    onCompleted(memorySource.wasFlashcardsPassed(idSafe))
                    memorySource.flashcardsWasPassed(idSafe)
                }
            }
        }
    }

    fun dontRememberCard() {
        val current = _flashcards.value ?: return
        if (current.isNotEmpty()) {
            val first = current.first()
            _flashcards.value = current.drop(1) + first
        }
    }
}