package com.teils.main_menu.presentation.screens.gamificationScreen

import androidx.lifecycle.ViewModel
import com.teils.main_menu.data.sources.XpSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GamificationScreenViewModel @Inject constructor(
    private val xpSource: XpSource
) : ViewModel() {
    private val _xp = MutableStateFlow(XpModel(0, 0, 0))
    val xp: StateFlow<XpModel> = _xp

    init {
        loadData()
    }

    fun loadData() {
        _xp.value = XpModel(
            vocabulary = xpSource.vocabulary,
            grammar = xpSource.grammar,
            communication = xpSource.communication
        )
    }
}