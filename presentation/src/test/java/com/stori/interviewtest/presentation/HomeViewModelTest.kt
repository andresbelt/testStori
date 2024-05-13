package com.stori.interviewtest.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.stori.interviewtest.commons.Either
import com.stori.interviewtest.data.models.Movement
import com.stori.interviewtest.domain.usecase.AuthProfileUseCase
import com.stori.interviewtest.domain.usecase.GetMovementsUseCase
import com.stori.interviewtest.domain.usecase.GetProfileUseCase
import com.stori.interviewtest.ui.screens.home.HomeScreenViewEvent.Init
import com.stori.interviewtest.ui.screens.home.HomeScreenViewState.ListDisplay
import com.stori.interviewtest.ui.screens.home.HomeViewModel
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule: CoroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: HomeViewModel

    @RelaxedMockK
    private lateinit var getAuthUseCase: AuthProfileUseCase

    @RelaxedMockK
    private lateinit var getMovements: GetMovementsUseCase

    @RelaxedMockK
    private lateinit var getProfile: GetProfileUseCase


    // Properties
    private val counter = Movement("Winning team", "10", "qwerty")

    @After
    fun tearDown() = clearAllMocks()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = HomeViewModel(getProfile,getMovements, getAuthUseCase)
    }

    @After
    fun verifyAll() {
        clearAllMocks()
    }

    @Test
    fun `sing in state ListDisplay`() = runTest {
        val expected = createFakeNewsResponse()

        coEvery { getMovements.getlistMovements() } returns flow {
            emit(Either.Right(expected))
        }

        val event = Init

        with(viewModel) {
            postEvent(event)
        }

        assertEquals(expected, viewModel.listMovements.value)
    }

    private fun createFakeNewsResponse(): List<Movement> {
        return arrayListOf(counter)
    }
}
