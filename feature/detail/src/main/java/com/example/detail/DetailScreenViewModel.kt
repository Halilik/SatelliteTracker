package com.example.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.SatellitesRepository
import com.example.model.PositionsDataModel
import com.example.model.SatellitesDetailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val satellitesRepository: SatellitesRepository
) : ViewModel() {

    private val _satelliteDetailState =
        MutableStateFlow<SatellitesDetailState>(SatellitesDetailState.Loading)
    val satelliteDetailState: StateFlow<SatellitesDetailState> =
        _satelliteDetailState.asStateFlow()

    private val _satellitePositionState =
        MutableStateFlow(PositionsDataModel())
    val satellitePositionState: StateFlow<PositionsDataModel> =
        _satellitePositionState.asStateFlow()


    private var satelliteName = ""

    fun getSatellitesDetail(id: Int, satelliteName: String) {
        viewModelScope.launch {
            //    delay(1000L)
            satellitesRepository.getSatellitesDetails(id, satelliteName).let { satellite ->
                satellite?.let { _satelliteDetailState.emit(SatellitesDetailState.Success(it)) }
            }
        }
    }

    fun getSatellitesPosition(id: Int) {
        viewModelScope.launch {
            satellitesRepository.getSatellitesPositions().let { satellitesPositions ->
                satellitesPositions.list?.firstOrNull { it?.id == id.toString() }
                    ?.let { satellitePosition ->
                        satellitePosition.positions?.forEach { satellitePositionModel ->
                            satellitePositionModel?.let {
                                _satellitePositionState.emit(it)
                                delay(3000L)
                            }
                        }
                    }
            }

        }


    }

    fun setSatelliteName(satelliteName: String) {
        this.satelliteName = satelliteName
    }
}

sealed class SatellitesDetailState {
    data object Loading : SatellitesDetailState()
    data class Success(val data: SatellitesDetailModel) : SatellitesDetailState()
    data class Error(val message: String) : SatellitesDetailState()
}
