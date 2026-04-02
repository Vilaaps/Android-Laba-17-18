package com.example.myapplication.data

data class WeatherData(
    val temperature: Int? = null,
    val humidiry: Int? = null,
    val windSpeed: Int? = null,
    val isLoading: Boolean = false,
    var error: String? = null
)