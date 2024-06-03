package com.rektstudios.trueweather.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rektstudios.trueweather.MainDispatcherRule
import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.mapper.toWeatherDataDay
import com.rektstudios.trueweather.data.mapper.toWeatherDataHour
import com.rektstudios.trueweather.data.repository.FakeCityRepository
import com.rektstudios.trueweather.data.repository.FakeGeocodeHelper
import com.rektstudios.trueweather.data.repository.FakeLocationTracker
import com.rektstudios.trueweather.data.repository.FakeRealmDao
import com.rektstudios.trueweather.data.repository.FakePrefsRepository
import com.rektstudios.trueweather.data.repository.FakeWeatherRepository
import com.rektstudios.trueweather.domain.usecase.CurrentCityUseCase
import com.rektstudios.trueweather.domain.usecase.GetCityListUseCase
import com.rektstudios.trueweather.domain.usecase.GetCityNameFromLocationUseCase
import com.rektstudios.trueweather.domain.usecase.GetCurrentWeatherUseCase
import com.rektstudios.trueweather.domain.usecase.GetForecastWeatherUseCase
import com.rektstudios.trueweather.domain.usecase.UserPrefsUseCase
import com.rektstudios.trueweather.domain.util.Constants.KEY_CITY_NAME
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel:WeatherViewModel


    private lateinit var userPrefsUseCase: UserPrefsUseCase
    private lateinit var currentCityUseCase: CurrentCityUseCase
    private lateinit var getCityNameFromLocationUseCase: GetCityNameFromLocationUseCase
    private lateinit var getCityListUseCase: GetCityListUseCase
    private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase
    private lateinit var getForecastWeatherUseCase: GetForecastWeatherUseCase

    private lateinit var fakeWeatherRepository: FakeWeatherRepository
    private lateinit var fakePrefsRepository: FakePrefsRepository
    private lateinit var fakeCityRepository: FakeCityRepository
    private lateinit var fakeGeocodeHelper: FakeGeocodeHelper
    private lateinit var fakeLocationTracker: FakeLocationTracker
    private lateinit var fakeRealmDao: FakeRealmDao

    @Before
    fun setUp() {
        fakeRealmDao = FakeRealmDao()
        fakeWeatherRepository = FakeWeatherRepository(fakeRealmDao)
        fakeGeocodeHelper = FakeGeocodeHelper()
        fakeCityRepository = FakeCityRepository(fakeRealmDao)
        fakePrefsRepository = FakePrefsRepository()
        fakeLocationTracker = FakeLocationTracker()
        userPrefsUseCase = UserPrefsUseCase(fakePrefsRepository)
        getCityListUseCase = GetCityListUseCase(fakeCityRepository)
        getCurrentWeatherUseCase = GetCurrentWeatherUseCase(fakeWeatherRepository)
        getForecastWeatherUseCase = GetForecastWeatherUseCase(fakeWeatherRepository)
        getCityNameFromLocationUseCase = GetCityNameFromLocationUseCase(fakeWeatherRepository,fakeGeocodeHelper)
        currentCityUseCase = CurrentCityUseCase(fakeCityRepository,fakePrefsRepository,fakeLocationTracker,getCityNameFromLocationUseCase)
        viewModel = WeatherViewModel(userPrefsUseCase,getCityListUseCase, getCurrentWeatherUseCase, getForecastWeatherUseCase, currentCityUseCase)
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `toggleMetric - changes value in user prefs`() = runTest{
        val flag = viewModel.isMetric.firstOrNull()
        viewModel.toggleMetric()
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()
        val testValue = viewModel.isMetric.firstOrNull()
        assertEquals(!flag!!,testValue)
    }
    @Test
    fun `toggleCelsius - changes value in user prefs`()= runTest{
        val flag = viewModel.isCelsius.firstOrNull()
        viewModel.toggleCelsius()
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()
        val testValue = viewModel.isCelsius.firstOrNull()
        assertEquals(!flag!!,testValue)
    }

    @Test
    fun `setCurrentCityAndWeather - current city is updated if city in realm`()= runTest{
        val city = CityItem("Sapporo, Japan")
        fakeRealmDao.addCity(city)
        viewModel.setCurrentCityAndWeather(city)
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()
        assertEquals(city.cityName,viewModel.currentCity.cityName)
    }

    @Test
    fun `setCurrentCityAndWeather - current city is not updated if city not in realm`()= runTest{
        val city = CityItem("Sapporo, Japan")
        viewModel.setCurrentCityAndWeather(city)
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()
        assertEquals("Tokyo, Japan",viewModel.currentCity.cityName)
    }

    @Test
    fun `init - current city weather is set automatically`()= runTest{
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()
        assertEquals(
            fakeWeatherRepository.fakeCurrentWeatherResponses[0].current!!.toWeatherDataHour().conditionText,
            viewModel.currentWeather.firstOrNull()?.conditionText
        )
    }

    @Test
    fun `setCurrentCityAndWeather - current city weather is changed on current city changed`()= runTest{
        val city = CityItem("Sapporo, Japan")
        fakeRealmDao.addCity(city)
        viewModel.setCurrentCityAndWeather(city)
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()
        assertEquals(
            fakeWeatherRepository.fakeCurrentWeatherResponses[1].current!!.toWeatherDataHour().conditionText,
            viewModel.currentWeather.firstOrNull()?.conditionText
        )
    }

    @Test
    fun `init - forecast city weather is set automatically`()= runTest{
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()
        assertEquals(
            fakeWeatherRepository.fakeForecastResponses[0].forecast?.forecastDay?.get(0)
                ?.toWeatherDataDay()!!.conditionText,
            viewModel.currentCityForecastWeather.first?.firstOrNull()?.conditionText
        )
        assertEquals(
            fakeWeatherRepository.fakeForecastResponses[0].forecast?.forecastDay?.get(0)
                ?.toWeatherDataHour()?.get(0)!!.conditionText,
            viewModel.currentCityForecastWeather.second?.firstOrNull()?.conditionText
        )
    }

    @Test
    fun `setCurrentCityAndWeather - forecast city weather is changed on current city changed`()= runTest{
        val city = CityItem("Sapporo, Japan")
        fakeRealmDao.addCity(city)
        viewModel.setCurrentCityAndWeather(city)
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()
        assertEquals(
            fakeWeatherRepository.fakeForecastResponses[1].forecast?.forecastDay?.get(0)
                ?.toWeatherDataDay()!!.conditionText,
            viewModel.currentCityForecastWeather.first?.firstOrNull()?.conditionText
        )
        assertEquals(
            fakeWeatherRepository.fakeForecastResponses[1].forecast?.forecastDay?.get(0)
                ?.toWeatherDataHour()?.get(0)!!.conditionText,
            viewModel.currentCityForecastWeather.second?.firstOrNull()?.conditionText
        )
    }

    @Test
    fun `setCurrentCityFromGPS - current city is updated`()= runTest{
        fakeLocationTracker.setLocation(34.05, -118.24)
        viewModel.setCurrentCityFromGPS()
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()
        assertEquals("Los Angeles, United States",viewModel.currentCity.cityName)
    }

    @Test
    fun `init - current city is set from prefs data store`()= runTest{
        fakeLocationTracker.setLocation(0.0, 0.0)
        fakePrefsRepository.saveValue(KEY_CITY_NAME,"Bangkok, Thailand")
        viewModel.setCurrentCityFromGPS()
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()
        assertEquals("Bangkok, Thailand",viewModel.currentCity.cityName)
    }

}