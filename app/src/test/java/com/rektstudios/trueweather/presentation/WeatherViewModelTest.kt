package com.rektstudios.trueweather.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rektstudios.trueweather.data.repository.FakeCityRepository
import com.rektstudios.trueweather.data.repository.FakeGeocodeHelper
import com.rektstudios.trueweather.data.repository.FakeLocationTracker
import com.rektstudios.trueweather.data.repository.FakeUserPrefsRepository
import com.rektstudios.trueweather.data.repository.FakeWeatherRepository
import com.rektstudios.trueweather.domain.usecase.CurrentCityUseCase
import com.rektstudios.trueweather.domain.usecase.GetCityListUseCase
import com.rektstudios.trueweather.domain.usecase.GetCityNameFromLocationUseCase
import com.rektstudios.trueweather.domain.usecase.GetCurrentWeatherUseCase
import com.rektstudios.trueweather.domain.usecase.GetForecastWeatherUseCase
import com.rektstudios.trueweather.domain.usecase.UserPrefsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel:WeatherViewModel


    private lateinit var userPrefsUseCase: UserPrefsUseCase
    private lateinit var currentCityUseCase: CurrentCityUseCase
    private lateinit var getCityNameFromLocationUseCase: GetCityNameFromLocationUseCase
    private lateinit var getCityListUseCase: GetCityListUseCase
    private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase
    private lateinit var getForecastWeatherUseCase: GetForecastWeatherUseCase

    private lateinit var fakeWeatherRepository: FakeWeatherRepository
    private lateinit var fakeUserPrefsRepository: FakeUserPrefsRepository
    private lateinit var fakeCityRepository: FakeCityRepository
    private lateinit var fakeGeocodeHelper: FakeGeocodeHelper
    private lateinit var fakeLocationTracker: FakeLocationTracker

    @Before
    fun setUp() {
        fakeWeatherRepository = FakeWeatherRepository()
        fakeGeocodeHelper = FakeGeocodeHelper()
        fakeCityRepository = FakeCityRepository()
        fakeUserPrefsRepository = FakeUserPrefsRepository()
        fakeLocationTracker = FakeLocationTracker()
        userPrefsUseCase = UserPrefsUseCase(fakeUserPrefsRepository)
        getCityListUseCase = GetCityListUseCase(fakeCityRepository)
        getCurrentWeatherUseCase = GetCurrentWeatherUseCase(fakeWeatherRepository)
        getForecastWeatherUseCase = GetForecastWeatherUseCase(fakeWeatherRepository)
        getCityNameFromLocationUseCase = GetCityNameFromLocationUseCase(fakeWeatherRepository,fakeGeocodeHelper)
        currentCityUseCase = CurrentCityUseCase(fakeCityRepository,fakeUserPrefsRepository,fakeLocationTracker,getCityNameFromLocationUseCase)
        viewModel = WeatherViewModel(userPrefsUseCase,getCityListUseCase, getCurrentWeatherUseCase, getForecastWeatherUseCase, currentCityUseCase)
        dispatcher.scheduler.advanceUntilIdle()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `toggling metric user prefs changes boolean value`(){
        val flag = if(viewModel.isMetric.isInitialized) viewModel.isMetric.value else false
        viewModel.toggleMetric()
        dispatcher.scheduler.advanceUntilIdle()
        assertNotEquals(flag,viewModel.isMetric.value)
    }
}