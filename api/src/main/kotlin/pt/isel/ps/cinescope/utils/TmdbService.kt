package pt.isel.ps.cinescope.utils

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import pt.isel.ps.cinescope.domain.*

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

    fun getSerieDetails(sid: Int) =
        fetch("tv/$sid?$TMDB_API_KEY").bodyToMono<SeriesDetails>().block()

    fun getSeasonDetails(sid: Int, seasonNum: Int) =
        fetch("tv/$sid/season/$seasonNum?$TMDB_API_KEY").bodyToMono<SeasonDetails>().block()

    fun getEpisodeDetails(sid: Int, seasonNum: Int, epNum: Int) =
        fetch("tv/$sid/season/$seasonNum/episode/$epNum?$TMDB_API_KEY").bodyToMono<EpisodeDetails>().block()

    fun getSeriesWatchProviders(sid: Int?) =
        fetch("tv/$sid/watch/providers?$TMDB_API_KEY").bodyToMono<WatchProviders>().block()
}