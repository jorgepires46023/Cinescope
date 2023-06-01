package com.example.cinescope.domain.searches

import com.google.gson.annotations.SerializedName

data class ProviderInfo(
    @SerializedName("logo_path") val logoPath: String,
    @SerializedName("provider_name") val providerName: String
)