package com.example.data.di

data class Configuration(val apiUrl: String, val apiKey: String) {
    companion object {
        val DEFAULT =
            Configuration(
                apiUrl = "https://pixabay.com",
                apiKey = "44168800-16dceccd47b0c144f3bb8b30b"
            )
    }

}