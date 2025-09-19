package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.SatellitesRepository
import com.example.model.SatellitesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val satellitesRepository: SatellitesRepository
) : ViewModel() {
    private val _satellitesState = MutableStateFlow<SatellitesState>(SatellitesState.Loading)
    val satellitesState: StateFlow<SatellitesState> = _satellitesState.asStateFlow()

    init {
        getSatellites()
    }


    fun getSatellites() {
        viewModelScope.launch {
            delay(1000L)
            satellitesRepository.getSatellites().let { satellites ->
                _satellitesState.value = SatellitesState.Success(satellites)
            }
        }

    }
}

sealed class SatellitesState {
    data object Loading : SatellitesState()
    data class Success(val data: List<SatellitesModel>) : SatellitesState()
    data class Error(val message: String) : SatellitesState()
}