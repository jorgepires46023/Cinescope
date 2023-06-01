package com.example.cinescope.domain.searches

import com.google.gson.annotations.SerializedName

data class ExternalIds(
    @SerializedName("imdb_id") val imdbId: String,
    @SerializedName("facebook_id") val facebookId: String,
    @SerializedName("twitter_id") val twitterId: String
)