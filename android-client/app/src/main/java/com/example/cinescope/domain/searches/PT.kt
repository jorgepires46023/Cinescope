package com.example.cinescope.domain.searches

data class PT(
    val flatrate: List<ProviderInfo>?,
    val buy: List<ProviderInfo>?,
    val rent: List<ProviderInfo>?
)