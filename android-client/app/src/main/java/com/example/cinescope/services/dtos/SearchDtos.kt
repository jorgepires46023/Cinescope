package com.example.cinescope.services.dtos

data class ContentAPIDto(
    val page: Int,
    val results: List<Results>,
    val total_results: Int,
    val total_pages: Int
)

data class MovieInfo(
    val movieDetails: MovieDetails,
    val watchProviders: WatchProviders,
    val externalIds: ExternalIds
)

data class SeriesInfo(
    val seriesDetails: SeriesDetails,
    val watchProviders: WatchProviders,
    val externalIds: ExternalIds
)

data class EpisodeInfo(
    val episodeDetails: EpisodeDetails,
    val externalIds: ExternalIds
)

//******************************** AUX Classes to Dto **********************************************
data class EpisodeDetails(
    val air_date: String,
    val episode_number: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val stil_path: String
)
data class Results(
    val poster_path: String,
    val id: Int,
    val title: String,
    val name: String,
    val media_type: String,
    val popularity: Int
)

data class SeriesDetails(
    val id: Int,
    val name: String,
    val seasons: List<Seasons>,
    val status: String,
    val poster_path: String
)

data class Seasons(
    val episode_count: Int,
    val id: Int,
    val name: String,
    val season_number: Int
)

data class WatchProviders(
    val id: Int,
    val results: PT
)

data class ProviderInfo(
    val logo_path: String,
    val provider_name: String
)

/** Class who represents where provided content can be found in Portugal **/
data class PT(
    val link: String,
    val flatrate: List<ProviderInfo>?,
    val rent: List<ProviderInfo>?,
    val buy: List<ProviderInfo>?
)

data class ExternalIds(
    val imdb_id: String,
    val facebook_id: String,
    val twitter_id: String
)

//MovieDetails from API response
data class MovieDetails(
    val id: Int,
    val imdb_id: String,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val runtime: Int,
    val status: String,
    val title: String
)