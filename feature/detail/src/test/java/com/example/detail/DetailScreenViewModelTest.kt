package com.example.detail

import app.cash.turbine.test
import com.example.data.repository.SatellitesRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.stub

class DetailScreenViewModelTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var mockSatellitesRepository: SatellitesRepository

    private lateinit var viewModel: DetailScreenViewModel

    @Before
    fun setUp() {
        viewModel = DetailScreenViewModel(mockSatellitesRepository)
    }

    @Test
    fun `getSatellitesDetail initialState is Loading and then Finished`() {
        runTest {
            val expectedResponse = PreviewAndTestData.dummySatelliteDetail
            mockSatellitesRepository.stub {
                onBlocking {
                    getSatellitesDetails(1, "Starship-1")
                } doAnswer {
                    expectedResponse
                }
            }
            viewModel.satelliteDetailState.test {
                viewModel.getSatellitesDetail(1, "Starship-1")
                assertEquals(SatellitesDetailState.Loading, awaitItem())
                assertEquals(SatellitesDetailState.Success(expectedResponse), awaitItem())
            }
        }
    }
}