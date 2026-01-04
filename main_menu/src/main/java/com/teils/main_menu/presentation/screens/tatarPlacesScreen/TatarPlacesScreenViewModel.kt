package com.teils.main_menu.presentation.screens.tatarPlacesScreen

import androidx.lifecycle.ViewModel
import com.teils.tellem.core.utils.places.PlaceModel
import com.teils.tellem.data_source.data.repositories.PlacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TatarPlacesScreenViewModel @Inject constructor(
    private val placesRepository: PlacesRepository
) : ViewModel() {
    private val _places = MutableStateFlow(emptyList<PlaceModel>())
    val places: StateFlow<List<PlaceModel>> = _places

    init {
        loadData()
    }

    private fun loadData() {
        _places.value = placesRepository.getPlaces()
    }
}