package pt.isel.ps.cinescope.domain

import com.fasterxml.jackson.annotation.JsonProperty
import java.text.SimpleDateFormat

private val TVMEDIATYPE = "tv"
private val MOVIEMEDIATYPE = "movie"

data class SearchDTO(val page: Int?, @JsonProperty("results")val resultDTOS: Array<ResultDTO>?, val total_results: Int?, val total_pages: Int?)

data class Search(val page: Int?, val results: List<Result>?, val total_results: Int?, val total_pages: Int?)

data class ResultDTO(val poster_path: String?, val id: Int?, val title: String?, val name: String?, val media_type: String?, val popularity: Int?, val adult: Boolean)

data class Result(val poster_path: String?, val id: Int?, val title: String?, val media_type: String?, val popularity: Int?, val adult: Boolean)

fun SearchDTO?.toSearch(adult: Boolean): Search?{
    if(this == null) return null
    val list = mutableListOf<Result>()
    this.resultDTOS?.forEach { res ->
        val result = res.toResult()
        if(result != null)
            list.add(result)
    }
    if(!adult)
        list.filter { result -> !result.adult }
    return Search(this.page, list, this.total_results, total_pages)
}

fun ResultDTO.toResult(): Result?{
    return if(this.media_type == TVMEDIATYPE)
        Result(this.poster_path, this.id, this.name, this.media_type, this.popularity, this.adult)
    else if (this.media_type == MOVIEMEDIATYPE)
        Result(this.poster_path, this.id, this.title, this.media_type, this.popularity, this.adult)
    else null
}


data class MovieDetails(
    val id: Int?,
    val imdb_id: String?,
    val original_title: String?,
    val overview: String?,
    val poster_path: String?,
    val backdrop_path: String?,
    val release_date: String?,
    val runtime: Int?,
    val status: String?,
    val title: String?,
    val date: String? = reformatDate(release_date)
)


fun reformatDate(release_date: String?): String?{
    if(release_date == null) return null
    val inputFormat = SimpleDateFormat("yyyy-mm-dd")
    val outputFormat = SimpleDateFormat("dd-mm-yyyy")
    val date = inputFormat.parse(release_date)
    return outputFormat.format(date)
}


data class SeriesDetails(
    val overview: String?,
    val id: Int?,
    val name: String?,
    val seasons: Array<Seasons>?,
    val status: String?,
    val poster_path: String?,
    val backdrop_path: String?
)


data class ExternalIds(val imdb_id: String?, val facebook_id: String?, val twitter_id: String?)

data class ImagesResponse(val backdrops: Array<Image>, val id: Int?)

data class Image(val height: Int?, val width: Int?, val file_path: String?)

data class Seasons(val episode_count: Int?, val id: Int?, val name: String?, val season_number: Int?)

data class SeasonDetails(val air_date: String?, val episodes: Array<EpisodeDetails>?, val season_number: Int?)

data class EpisodeDetails(
    val air_date: String?,
    val episode_number: Int?,
    val id: Int?,
    val name: String?,
    val overview: String?,
    val still_path:String?,
    val date: String? = reformatDate(air_date)
    )

data class WatchProviders(val id: Int?, val results: CountriesInfo?)

data class CountriesInfo(@JsonProperty("PT") val pt: CountryProviders?)

data class CountryProviders(val flatrate: Array<ProviderInfo>?, val rent: Array<ProviderInfo>?, val buy: Array<ProviderInfo>?)

data class ProviderInfo(val logo_path: String?, val provider_name: String?)