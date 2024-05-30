package com.rektstudios.trueweather.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rektstudios.trueweather.data.repository.FakeCityRepository
import com.rektstudios.trueweather.data.repository.FakeWeatherRepository
import com.rektstudios.trueweather.domain.usecase.AddCityUseCase
import com.rektstudios.trueweather.domain.usecase.DeleteCityUseCase
import com.rektstudios.trueweather.domain.usecase.GetCityListUseCase
import com.rektstudios.trueweather.domain.usecase.GetCitySuggestionsUseCase
import com.rektstudios.trueweather.domain.usecase.GetCurrentWeatherUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher

import org.junit.After
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
class CityViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel:CityViewModel

    private lateinit var addCityUseCase: AddCityUseCase
    private lateinit var deleteCityUseCase: DeleteCityUseCase
    private lateinit var getCityListUseCase: GetCityListUseCase
    private lateinit var getCitySuggestionsUseCase: GetCitySuggestionsUseCase
    private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

    private lateinit var fakeWeatherRepository: FakeWeatherRepository
    private lateinit var fakeCityRepository: FakeCityRepository

    @Before
    fun setUp() {
        fakeWeatherRepository = FakeWeatherRepository()
        fakeCityRepository = FakeCityRepository()
        addCityUseCase = AddCityUseCase(fakeCityRepository)
        deleteCityUseCase = DeleteCityUseCase(fakeCityRepository)
        getCityListUseCase = GetCityListUseCase(fakeCityRepository)
        getCitySuggestionsUseCase = GetCitySuggestionsUseCase(fakeCityRepository, fakeWeatherRepository)
        getCurrentWeatherUseCase = GetCurrentWeatherUseCase(fakeWeatherRepository)
        viewModel = CityViewModel(addCityUseCase,deleteCityUseCase,getCityListUseCase,getCitySuggestionsUseCase,getCurrentWeatherUseCase)
    }

    @After
    fun tearDown() {
    }
}