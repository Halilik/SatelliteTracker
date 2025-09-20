package com.example.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.SatellitesRepository
import com.example.model.SatellitesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val satellitesRepository: SatellitesRepository
) : ViewModel() {
    private var allSatelliteList = mutableListOf<SatellitesModel>()
    private val _satellitesState = MutableStateFlow<SatellitesState>(SatellitesState.Loading)
    val satellitesState: StateFlow<SatellitesState> = _satellitesState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        getSatellites()
        searchSatellites()
    }


    fun getSatellites() {
        viewModelScope.launch {
            //  delay(1000L)
            satellitesRepository.getSatellites().let { satellites ->
                _satellitesState.value = SatellitesState.Success(satellites)
                allSatelliteList = satellites.toMutableList()
            }
        }

    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    @OptIn(FlowPreview::class)
    fun searchSatellites() {
        _searchQuery.debounce(500L).distinctUntilChanged().onEach { query ->
            _satellitesState.value = SatellitesState.Loading
            delay(300L)
            if (query.isEmpty()) {
                _satellitesState.value = SatellitesState.Success(allSatelliteList)
            }

            val filteredSatellites = allSatelliteList.filter { satellite ->
                satellite.name?.contains(query, ignoreCase = true) == true
            }
            _satellitesState.value = SatellitesState.Success(filteredSatellites)

        }.launchIn(
            viewModelScope
        )


    }
}

sealed class SatellitesState {
    data object Loading : SatellitesState()
    data class Success(val data: List<SatellitesModel>) : SatellitesState()
    data class Error(val message: String) : SatellitesState()
}