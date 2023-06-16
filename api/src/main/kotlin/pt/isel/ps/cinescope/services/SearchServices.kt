package pt.isel.ps.cinescope.services

import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.domain.*
import pt.isel.ps.cinescope.utils.TmdbService


@Component
class SearchServices(val tmdbServices: TmdbService) {

    fun searchByQuery(input: String?, page: Int?): Search? {
        val p = page ?: 1
        if (input.isNullOrBlank()) return null
        val res = tmdbServices.fetchQuery(input, p) ?: return null
        val filter = res.results?.filter { r -> r.media_type == "tv" || r.media_type == "movie" }?.toTypedArray() ?: emptyArray()
        val diff = (res.results?.size)?.minus(filter.size) ?: 0
        return Search(res.page, filter, res.total_results?.minus(diff), res.total_pages)
    }

    fun movieDetails(id: Int?): MovieDetailsOutput?{
        if(id == null) return null
        val movieDetails = tmdbServices.getMovieDetails(id) ?: return null
        val externalIds = tmdbServices.getMoviesExternalId(id) ?: return null
        val watchProviders = tmdbServices.getMoviesWatchProviders(id) ?: return null
        val images = tmdbServices.getMovieImages(id) ?: return null
        val image =
            if(images.backdrops.isNotEmpty())
                images.backdrops.firstOrNull { s -> (s.height != null && s.height >= 1080)  && (s.width != null && s.width >= 1920) }
            else Image(null, null, movieDetails.backdrop_path ?: "")
        return MovieDetailsOutput(MovieDetails(movieDetails.id, movieDetails.imdb_id, movieDetails.original_title, movieDetails.overview, movieDetails.poster_path, image?.file_path ?: movieDetails.backdrop_path, movieDetails.release_date, movieDetails.runtime, movieDetails.status, movieDetails.title, movieDetails.date),
            watchProviders, externalIds)
    }

    fun serieDetails(id: Int?): SeriesDetailsOutput?{
        if (id == null) return null
        val seriesDetails = tmdbServices.getSerieDetails(id) ?: return null
        val externalIds = tmdbServices.getSeriesExternalId(id) ?: return null
        val watchProviders = tmdbServices.getSeriesWatchProviders(id) ?: return null
        val images = tmdbServices.getSerieImages(id) ?: return null
        val image =
            if(images.backdrops.isNotEmpty())
                images.backdrops.firstOrNull { s -> (s.height != null && s.height >= 1080)  && (s.width != null && s.width >= 1920)}
            else Image(null, null, seriesDetails.backdrop_path?: "")
        return SeriesDetailsOutput(
            SeriesDetails(seriesDetails.overview, seriesDetails.id, seriesDetails.name, seriesDetails.seasons, seriesDetails.status, seriesDetails.poster_path, image?.file_path ?: seriesDetails.backdrop_path),
            watchProviders, externalIds)
    }

    fun seasonDetails(id: Int?, seasonNum: Int?): SeasonDetailsOutput?{
        if(id == null || seasonNum == null) return null
        val seasonDetails = tmdbServices.getSeasonDetails(id, seasonNum) ?: return null
        val watchProviders = tmdbServices.getSeasonWatchProviders(id, seasonNum) ?: return null
        return SeasonDetailsOutput(seasonDetails, watchProviders)
    }

    fun episodeDetails(id: Int?, seasonNum: Int?, epNum: Int?): EpisodeDetailOutput?{
        if(id == null || seasonNum == null || epNum == null) return null
        val episodeDetails = tmdbServices.getEpisodeDetails(id, seasonNum, epNum) ?: return null
        val externalIds = tmdbServices.getEpisodeExternalId(id, seasonNum, epNum) ?: return null
        return EpisodeDetailOutput(episodeDetails, externalIds)
    }

    fun getPopularMovies(page: Int?): Search? {
        val p = page ?: 1
        return tmdbServices.getPopularMovies(p)
    }

    fun getPopularSeries(page: Int?): Search? {
        val p = page ?: 1
        return tmdbServices.getPopularSeries(p)
    }

    fun getMovieRecommendations(id: Int?, page: Int?): Search? {
        val p = page ?: 1
        if (id == null) return null
        return tmdbServices.getMovieRecommendations(id, p)
    }

    fun getSerieRecommendations(id: Int?, page: Int?): Search? {
        val p = page ?: 1
        if (id == null) return null
        return tmdbServices.getSerieRecommendations(id, p)
    }

}