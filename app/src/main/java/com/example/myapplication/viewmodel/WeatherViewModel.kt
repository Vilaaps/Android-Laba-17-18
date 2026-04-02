package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.WeatherData
import com.example.myapplication.data.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class WeatherViewModel : ViewModel() {
    private val repository = WeatherRepository()
    private val _weatherState = MutableStateFlow(WeatherData())
    val weatherState: StateFlow<WeatherData> = _weatherState.asStateFlow()

    init {
        loadWeatherData()
    }

    fun loadWeatherData() {
        viewModelScope.launch {
            _weatherState.value = _weatherState.value.copy(
                isLoading = true,
                error = null
            )
            try {
                val temperature = repository.fetchTemperature()
                _weatherState.value = _weatherState.value.copy(temperature = temperature)
                val humidity = repository.fetchHumidity()
                _weatherState.value = _weatherState.value.copy(humidity = humidity)
                val windSpeed = repository.fetchWindspeed()
                _weatherState.value = _weatherState.value.copy(windSpeed)
                _weatherState.value = _weatherState.value.copy(isLoading = false)
            } catch (e: Exception) {
                _weatherState.value = _weatherState.value.copy(
                    isLoading = false,
                    error = "Ошибка загрузки: ${e.message}"
                )
            }
        }
    }
}