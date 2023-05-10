package pt.isel.ps.cinescope.utils

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import pt.isel.ps.cinescope.domain.*
import java.util.Objects

const val TMDB_API_KEY = "api_key=1f54ddc9f7ff5ecab4807e013dddc65b"
const val TMDB_URL = "https://api.themoviedb.org/3/"

@Service
class TmdbService {

    private fun fetch(path: String): WebClient.ResponseSpec{
        val client = WebClient.create(TMDB_URL)
        return client.get().uri(path).retrieve()
    }

    fun fetchQuery(input: String) =
        fetch("search/multi?$TMDB_API_KEY&page=1&query=$input").bodyToMono<Search>().block()

    fun getMovieDetails(mid: Int) =
        fetch("movie/$mid?$TMDB_API_KEY").bodyToMono<MovieDetails>().block()

    fun getMoviesWatchProviders(mid: Int) =
        fetch("movie/$mid/watch/providers?$TMDB_API_KEY").bodyToMono<WatchProviders>().block()

    fun getMoviesExternalId(mid: Int) =
        fetch("movie/$mid/external_ids?$TMDB_API_KEY").bodyToMono<ExternalIds>().block()

    fun getSerieDetails(sid: Int) =
        fetch("tv/$sid?$TMDB_API_KEY").bodyToMono<SeriesDetails>().block()

    fun getSeasonDetails(sid: Int, seasonNum: Int) =
        fetch("tv/$sid/season/$seasonNum?$TMDB_API_KEY").bodyToMono<SeasonDetails>().block()

    fun getEpisodeDetails(sid: Int, seasonNum: Int, epNum: Int) =
        fetch("tv/$sid/season/$seasonNum/episode/$epNum?$TMDB_API_KEY").bodyToMono<EpisodeDetails>().block()

    fun getSeriesWatchProviders(sid: Int) =
        fetch("tv/$sid/watch/providers?$TMDB_API_KEY").bodyToMono<WatchProviders>().block()

    fun getSeriesExternalId(sid: Int) =
        fetch("tv/$sid/external_ids?$TMDB_API_KEY").bodyToMono<ExternalIds>().block()

    fun getEpisodeExternalId(sid: Int, season: Int, epNum: Int) =
        fetch("tv/$sid/season/$season/episode/$epNum/external_ids?$TMDB_API_KEY").bodyToMono<ExternalIds>().block()

    fun getPopularMovies() =
        fetch("trending/movie/day?$TMDB_API_KEY").bodyToMono<Search>().block()

    fun getPopularSeries() =
        fetch("trending/tv/day?$TMDB_API_KEY").bodyToMono<Search>().block()

    fun getMovieRecommendations(id: Int) =
        fetch("movie/$id/recommendations?$TMDB_API_KEY").bodyToMono<Search>().block()

    fun getSerieRecommendations(id: Int) =
        fetch("tv/$id/recommendations?$TMDB_API_KEY").bodyToMono<Search>().block()
}