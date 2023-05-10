package pt.isel.ps.cinescope.services

import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.domain.*
import pt.isel.ps.cinescope.utils.TmdbService

//TODO serieDetails/MovieDetails permitir null object ou lancar exception?
//TODO serieDetails/MovieDetails external id request (se ja tiver em db nao faz sentido realizar)

@Component
class SearchServices(val tmdbServices: TmdbService) {

    fun searchByQuery(input: String?): Search? {
        if (input.isNullOrBlank()) return null
        return tmdbServices.fetchQuery(input)
    }

    fun movieDetails(id: Int?): MovieDetailsOutput?{
        if(id == null) return null
        val movieDetails = tmdbServices.getMovieDetails(id) ?: return null
        val externalIds = tmdbServices.getMoviesExternalId(id) ?: return null
        val watchProviders = tmdbServices.getMoviesWatchProviders(id) ?: return null
        return MovieDetailsOutput(movieDetails, watchProviders, externalIds)
    }

    fun serieDetails(id: Int?): SeriesDetailsOutput?{
        if (id == null) return null
        val seriesDetails = tmdbServices.getSerieDetails(id) ?: return null
        val externalIds = tmdbServices.getSeriesExternalId(id) ?: return null
        val watchProviders = tmdbServices.getSeriesWatchProviders(id) ?: return null
        return SeriesDetailsOutput(seriesDetails, watchProviders, externalIds)
    }

    fun seasonDetails(id: Int?, seasonNum: Int?): SeasonDetails?{
        if(id == null || seasonNum == null) return null
        return tmdbServices.getSeasonDetails(id, seasonNum)
    }

    fun episodeDetails(id: Int?, seasonNum: Int?, epNum: Int?): EpisodeDetailOutput?{
        if(id == null || seasonNum == null || epNum == null) return null
        val episodeDetails = tmdbServices.getEpisodeDetails(id, seasonNum, epNum) ?: return null
        val externalIds = tmdbServices.getEpisodeExternalId(id, seasonNum, epNum) ?: return null
        return EpisodeDetailOutput(episodeDetails, externalIds)
    }

    fun getPopularMovies(): Search?{
        val movies = tmdbServices.getPopularMovies()
        if (movies != null)
            movies.results?.forEach { it.media_type = "movie" }
        return movies
    }

    fun getPopularSeries(): Search?{
        val series = tmdbServices.getPopularSeries()
        if (series != null)
            series.results?.forEach {it.media_type = "tv"}
        return series
    }

    fun getMovieRecommendations(id:Int?): Search?{
        if(id == null) return null
        val movies = tmdbServices.getMovieRecommendations(id)
        return movies
    }

    fun getSerieRecommendations(id: Int?): Search?{
        if(id == null) return null
        val series = tmdbServices.getSerieRecommendations(id)
        return series
    }

}