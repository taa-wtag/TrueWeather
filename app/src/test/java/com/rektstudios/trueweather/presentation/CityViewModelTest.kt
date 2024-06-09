package com.rektstudios.trueweather.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rektstudios.trueweather.MainDispatcherRule
import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.repository.FakeCityRepository
import com.rektstudios.trueweather.data.repository.FakeRealmDao
import com.rektstudios.trueweather.data.repository.FakePrefsRepository
import com.rektstudios.trueweather.data.repository.FakeWeatherRepository
import com.rektstudios.trueweather.domain.usecase.AddCityUseCase
import com.rektstudios.trueweather.domain.usecase.DeleteCityUseCase
import com.rektstudios.trueweather.domain.usecase.GetCityListUseCase
import com.rektstudios.trueweather.domain.usecase.GetCitySuggestionsUseCase
import com.rektstudios.trueweather.domain.usecase.GetCurrentWeatherUseCase
import com.rektstudios.trueweather.domain.usecase.UserPrefsUseCase
import com.rektstudios.trueweather.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CityViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel:CityViewModel

    private lateinit var addCityUseCase: AddCityUseCase
    private lateinit var deleteCityUseCase: DeleteCityUseCase
    private lateinit var getCityListUseCase: GetCityListUseCase
    private lateinit var getCitySuggestionsUseCase: GetCitySuggestionsUseCase
    private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase
    private lateinit var userPrefsUseCase: UserPrefsUseCase

    private lateinit var fakeWeatherRepository: FakeWeatherRepository
    private lateinit var fakeCityRepository: FakeCityRepository
    private lateinit var fakePrefsRepository: FakePrefsRepository
    private lateinit var fakeRealmDao: FakeRealmDao

    @Before
    fun setUp() {
        fakePrefsRepository = FakePrefsRepository()
        fakeRealmDao = FakeRealmDao()
        fakeWeatherRepository = FakeWeatherRepository(fakeRealmDao)
        fakeCityRepository = FakeCityRepository(fakeRealmDao)
        addCityUseCase = AddCityUseCase(fakeCityRepository)
        deleteCityUseCase = DeleteCityUseCase(fakeCityRepository)
        getCityListUseCase = GetCityListUseCase(fakeCityRepository)
        getCitySuggestionsUseCase = GetCitySuggestionsUseCase(fakeCityRepository, fakeWeatherRepository)
        getCurrentWeatherUseCase = GetCurrentWeatherUseCase(fakeWeatherRepository)
        userPrefsUseCase = UserPrefsUseCase(fakePrefsRepository)
        viewModel = CityViewModel(userPrefsUseCase, addCityUseCase,deleteCityUseCase,getCityListUseCase,getCitySuggestionsUseCase,getCurrentWeatherUseCase)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `addCity - checks added to cache`() = runTest{
        val city = CityItem("Dhaka, India")
        viewModel.addCity(city.cityName)
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()
        val testCity = fakeRealmDao.getCity(city.cityName)
        Assert.assertEquals(city.cityName,testCity?.cityName)
    }

    @Test
    fun `addCity - checks cities flow updated`() = runTest{
        val city = CityItem("Dhaka, India")
        viewModel.addCity(city.cityName)
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()
        val testCity= viewModel.cities.firstOrNull()?.firstOrNull()
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()
        Assert.assertEquals(city.cityName,testCity?.cityName)
    }

    @Test
    fun `deleteCity - checks deleted from cache`() = runTest{
        val city = CityItem("Dhaka, India")
        viewModel.addCity(city.cityName)
        viewModel.deleteCity(city)
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()
        val testCity = fakeRealmDao.getCity(city.cityName)
        Assert.assertEquals(null,testCity)
    }


    @Test
    fun `searchCities - checks valid response returned`() = runTest{
        viewModel.searchCities("To")
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()
        val testCity = viewModel.suggestedCities.getOrAwaitValue().firstOrNull()
        if (testCity != null) {
            Assert.assertEquals("Tokyo, Japan",testCity.cityName)
        }
    }

    @Test
    fun `searchCities - checks empty query returns null`() = runTest{
        viewModel.searchCities("")
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()
        val testCity = viewModel.suggestedCities.getOrAwaitValue().firstOrNull()
        Assert.assertEquals(null,testCity)
    }

    @Test
    fun `searchCities - checks invalid query returns null`() = runTest{
        viewModel.searchCities("Da")
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()
        val testCity = viewModel.suggestedCities.getOrAwaitValue().firstOrNull()
        Assert.assertEquals(null,testCity)
    }

}