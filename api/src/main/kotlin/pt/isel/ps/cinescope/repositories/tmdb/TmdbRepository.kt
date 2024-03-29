package pt.isel.ps.cinescope.repositories.tmdb

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import pt.isel.ps.cinescope.domain.*
import pt.isel.ps.cinescope.services.exceptions.InternalServerErrorException


const val TMDB_API_KEY = "api_key=1f54ddc9f7ff5ecab4807e013dddc65b"
const val TMDB_URL = "https://api.themoviedb.org/3/"

@Service
class TmdbRepository {

    val client = WebClient.builder()
        .baseUrl(TMDB_URL)
        .exchangeStrategies(
            ExchangeStrategies.builder().codecs{
                it.defaultCodecs().maxInMemorySize(1000000)
            }.build()
        )
        .build()


    private fun fetch(path: String): WebClient.ResponseSpec{
        return client.get().uri(path).retrieve()
    }

    fun fetchQuery(input: String, page: Int) =
        fetch("search/multi?$TMDB_API_KEY&page=$page&query=$input")
            .bodyToMono<SearchDTO>()
            .onErrorMap { e -> throw InternalServerErrorException("external api error: ${e.localizedMessage}") }
            .block()

    fun getMovieDetails(mid: Int) =
        fetch("movie/$mid?$TMDB_API_KEY")
            .bodyToMono<MovieDetails>()
            .onErrorMap { e -> throw InternalServerErrorException("external api error: ${e.localizedMessage}") }
            .block()

    fun getMoviesWatchProviders(mid: Int) =
        fetch("movie/$mid/watch/providers?$TMDB_API_KEY").bodyToMono<WatchProviders>().block()

    fun getMoviesExternalId(mid: Int) =
        fetch("movie/$mid/external_ids?$TMDB_API_KEY")
            .bodyToMono<ExternalIds>()
            .onErrorMap { e -> throw InternalServerErrorException("external api error: ${e.localizedMessage}") }
            .block()

    fun getSerieDetails(sid: Int) =
        fetch("tv/$sid?$TMDB_API_KEY")
            .bodyToMono<SeriesDetails>()
            .onErrorMap { e -> throw InternalServerErrorException("external api error: ${e.localizedMessage}") }
            .block()

    fun getSerieImages(sid: Int) =
        fetch("tv/$sid/images?$TMDB_API_KEY")
            .bodyToMono<ImagesResponse>()
            .onErrorMap { e -> throw InternalServerErrorException("external api error: ${e.localizedMessage}") }
            .block()

    fun getMovieImages(sid: Int) =
        fetch("movie/$sid/images?$TMDB_API_KEY")
            .bodyToMono<ImagesResponse>()
            .onErrorMap { e -> throw InternalServerErrorException("external api error: ${e.localizedMessage}") }
            .block()

    fun getSeasonDetails(sid: Int, seasonNum: Int) =
        fetch("tv/$sid/season/$seasonNum?$TMDB_API_KEY")
            .bodyToMono<SeasonDetails>()
            .onErrorMap { e -> throw InternalServerErrorException("external api error: ${e.localizedMessage}") }
            .block()

    fun getSeriesWatchProviders(sid: Int) =
        fetch("tv/$sid/watch/providers?$TMDB_API_KEY")
            .bodyToMono<WatchProviders>()
            .onErrorMap { e -> throw InternalServerErrorException("external api error: ${e.localizedMessage}") }
            .block()

    fun getEpisodeDetails(sid: Int, seasonNum: Int, epNum: Int) =
        fetch("tv/$sid/season/$seasonNum/episode/$epNum?$TMDB_API_KEY")
            .bodyToMono<EpisodeDetails>()
            .onErrorMap { e -> throw InternalServerErrorException("external api error: ${e.localizedMessage}") }
            .block()

    fun getSeasonWatchProviders(sid: Int, season: Int) =
        fetch("tv/$sid/season/$season/watch/providers?$TMDB_API_KEY")
            .bodyToMono<WatchProviders>().onErrorMap { e -> throw InternalServerErrorException("external api error: ${e.localizedMessage}") }
            .block()

    fun getSeriesExternalId(sid: Int) =
        fetch("tv/$sid/external_ids?$TMDB_API_KEY")
            .bodyToMono<ExternalIds>()
            .onErrorMap { e -> throw InternalServerErrorException("external api error: ${e.localizedMessage}") }
            .block()

    fun getEpisodeExternalId(sid: Int, season: Int, epNum: Int) =
        fetch("tv/$sid/season/$season/episode/$epNum/external_ids?$TMDB_API_KEY")
            .bodyToMono<ExternalIds>()
            .onErrorMap { e -> throw InternalServerErrorException("external api error: ${e.localizedMessage}") }
            .block()

    fun getPopularMovies(page: Int) =
        fetch("trending/movie/day?$TMDB_API_KEY&page=$page&include_adult=false")
            .bodyToMono<SearchDTO>()
            .onErrorMap { e -> throw InternalServerErrorException("external api error: ${e.localizedMessage}") }
            .block()

    fun getPopularSeries(page: Int) =
        fetch("trending/tv/day?$TMDB_API_KEY&page=$page&include_adult=false")
            .bodyToMono<SearchDTO>()
            .onErrorMap { e -> throw InternalServerErrorException("external api error: ${e.localizedMessage}") }
            .block()

    fun getMovieRecommendations(id: Int, page: Int) =
        fetch("movie/$id/recommendations?$TMDB_API_KEY&page=$page")
            .bodyToMono<SearchDTO>()
            .onErrorMap { e -> throw InternalServerErrorException("external api error: ${e.localizedMessage}") }
            .block()

    fun getSerieRecommendations(id: Int, page: Int) =
        fetch("tv/$id/recommendations?$TMDB_API_KEY&page=$page")
            .bodyToMono<SearchDTO>()
            .onErrorMap { e -> throw InternalServerErrorException("external api error: ${e.localizedMessage}") }
            .block()
}