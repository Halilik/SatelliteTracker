package com.example.home

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

class HomeScreenViewModelTest {


    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var mockSatellitesRepository: SatellitesRepository

    private lateinit var viewModel: HomeScreenViewModel


    @Before
    fun setUp() {
        viewModel = HomeScreenViewModel(mockSatellitesRepository)
    }

    @Test
    fun `getSatellites initialState is Loading and then Finished`() {
        runTest {
            val expectedResponse = PreviewAndTestData.dummySatellitesList
            mockSatellitesRepository.stub {
                onBlocking {
                    getSatellites()
                } doAnswer {
                    expectedResponse
                }
            }
            viewModel.satellitesState.test {
                viewModel.getSatellites()
                assertEquals(SatellitesState.Loading, awaitItem())
                assertEquals(SatellitesState.Success(expectedResponse), awaitItem())
            }
        }
    }


    @Test
    fun `onSearchQueryChanged updates searchQuery Flow`() {
        runTest {
            viewModel.searchQuery.test {
                val query = "Search Query"
                viewModel.onSearchQueryChanged(query)
                assertEquals(query, expectMostRecentItem())
            }
        }

    }

    @Test
    fun `onSearchQueryChanged updates searchQuery Flow with empty query`() {
        runTest {
            viewModel.searchQuery.test {
                val query = ""
                viewModel.onSearchQueryChanged(query)
                assertEquals(query, expectMostRecentItem())
            }
        }

    }


    @Test
    fun `onSearchQueryChanged updates searchQuery Flow with long string`() {
        runTest {
            viewModel.searchQuery.test {
                val query =
                    "anmlltszmuuobqwseamborsgygjjwlabanmlltszmuuobqwseamborsgygjjwlabanmlltszmuuobqwseamborsgygjjwlab"
                viewModel.onSearchQueryChanged(query)
                assertEquals(query, expectMostRecentItem())
            }
        }

    }
}