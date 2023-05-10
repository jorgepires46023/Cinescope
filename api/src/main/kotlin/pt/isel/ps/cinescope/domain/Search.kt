package pt.isel.ps.cinescope.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class Search(val page: Int?, val results: Array<Result>?, val total_results: Int?, val total_pages: Int?)

//TODO ficar apenas com title
data class Result(val poster_path: String?, val id: Int?, val title: String?, val name: String?, var media_type: String?, val popularity: Int?)

data class MovieDetails(
    val id: Int?,
    val imdb_id: String?,
    val original_title: String?,
    val overview: String?,
    val poster_path: String?,
    val release_date: String?,
    val runtime: Int?,
    val status: String?,
    val title: String?
)

data class MovieDetailsOutput(
    val movieDetails: MovieDetails,
    val watchProviders: WatchProviders,
    val externalIds: ExternalIds?
)

data class SeriesDetails(
    val created_by: Array<Creator>?,
    val id: Int?,
    val name: String?,
    val seasons: Array<Seasons>?,
    val status: String?,
    val poster_path: String?
)

data class SeriesDetailsOutput(
    val serieDetails: SeriesDetails,
    val watchProviders: WatchProviders,
    val externalIds: ExternalIds?
)

data class EpisodeDetailOutput(
    val episodeDetails: EpisodeDetails,
    val externalIds: ExternalIds?
)

data class ExternalIds(val imdb_id: String?, val facebook_id: String?, val twitter_id: String?)

data class Creator(val name: String?, val profile_path: String?)

data class Seasons(val episode_count: Int?, val id: Int?, val name: String?, val season_number: Int?)

data class SeasonDetails(val air_date: String?, val episodes: Array<EpisodeDetails>?, val season_number: Int?)

data class EpisodeDetails(val air_date: String?, val episode_number: Int?, val id: Int?, val name: String?, val overview: String?, val still_path:String?)

data class WatchProviders(val id: Int?, val results: CountriesInfo?)

data class CountriesInfo(@JsonProperty("PT") val pt: CountryProviders?) //TODO outros paises

data class CountryProviders(val link: String?, val flatrate: Array<ProviderInfo>?, val rent: Array<ProviderInfo>?, val buy: Array<ProviderInfo>?)

data class ProviderInfo(val display_priority: Int?, val provider_name: String?)