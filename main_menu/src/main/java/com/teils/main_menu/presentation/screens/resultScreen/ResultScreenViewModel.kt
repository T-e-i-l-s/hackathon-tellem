package com.teils.main_menu.presentation.screens.resultScreen

import androidx.lifecycle.ViewModel
import com.teils.main_menu.data.sources.XpSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultScreenViewModel @Inject constructor(
    private val xpSource: XpSource
) : ViewModel() {
    fun giveXp(type: ResultType) {
        when (type) {
            ResultType.LESSON -> {
                xpSource.grammar += 5
                xpSource.vocabulary += 5
                xpSource.communication += 5
            }

            ResultType.FLASHCARDS -> {
                xpSource.vocabulary += 10
            }

            ResultType.AI_CHAT -> {
                xpSource.grammar += 5
                xpSource.vocabulary += 5
                xpSource.communication += 20
            }
        }
    }
}