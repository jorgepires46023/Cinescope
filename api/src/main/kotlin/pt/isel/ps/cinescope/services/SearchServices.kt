package pt.isel.ps.cinescope.services

import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.domain.*
import pt.isel.ps.cinescope.repositories.tmdb.TmdbRepository


@Component
class SearchServices(val tmdbRepository: TmdbRepository) {


    fun searchByQuery(input: String?, page: Int?): Search? {
        val p = page ?: 1
        if (input.isNullOrBlank()) return null
        val res = tmdbRepository.fetchQuery(input, p).toSearch(true) ?: return null
        if(res.results == null) return res
        val results = res.results.sortedByDescending { it.popularity }
        val diff = (res.results.size).minus(results.size)
        return Search(res.page, results, res.total_results?.minus(diff), res.total_pages)
    }

    fun movieDetails(id: Int?): MovieDetailsOutput?{
        if(id == null) return null
        val movieDetails = tmdbRepository.getMovieDetails(id) ?: return null
        val externalIds = tmdbRepository.getMoviesExternalId(id) ?: return null
        val watchProviders = tmdbRepository.getMoviesWatchProviders(id) ?: return null
        val images = tmdbRepository.getMovieImages(id) ?: return null
        val image =
            if(images.backdrops.isNotEmpty())
                images.backdrops.firstOrNull { s -> (s.height != null && s.height >= 1080)  && (s.width != null && s.width >= 1920) }
            else Image(null, null, movieDetails.backdrop_path ?: "")
        return MovieDetailsOutput(MovieDetails(movieDetails.id, movieDetails.imdb_id, movieDetails.original_title, movieDetails.overview, movieDetails.poster_path, image?.file_path ?: movieDetails.backdrop_path, movieDetails.release_date, movieDetails.runtime, movieDetails.status, movieDetails.title, movieDetails.date),
            watchProviders, externalIds)
    }

    fun seriesDetails(id: Int?): SeriesDetailsOutput?{
        if (id == null) return null
        val seriesDetails = tmdbRepository.getSerieDetails(id) ?: return null
        val externalIds = tmdbRepository.getSeriesExternalId(id) ?: return null
        val watchProviders = tmdbRepository.getSeriesWatchProviders(id) ?: return null
        val images = tmdbRepository.getSerieImages(id) ?: return null
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
        val seasonDetails = tmdbRepository.getSeasonDetails(id, seasonNum) ?: return null
        val watchProviders = tmdbRepository.getSeasonWatchProviders(id, seasonNum) ?: return null
        return SeasonDetailsOutput(seasonDetails, watchProviders)
    }

    fun episodeDetails(id: Int?, seasonNum: Int?, epNum: Int?): EpisodeDetailOutput?{
        if(id == null || seasonNum == null || epNum == null) return null
        val episodeDetails = tmdbRepository.getEpisodeDetails(id, seasonNum, epNum) ?: return null
        val externalIds = tmdbRepository.getEpisodeExternalId(id, seasonNum, epNum) ?: return null
        return EpisodeDetailOutput(episodeDetails, externalIds)
    }

    fun getPopularMovies(page: Int?): Search? {
        val p = page ?: 1
        return tmdbRepository.getPopularMovies(p).toSearch(false)
    }

    fun getPopularSeries(page: Int?): Search? {
        val p = page ?: 1
        return tmdbRepository.getPopularSeries(p).toSearch(false)
    }

    fun getMovieRecommendations(id: Int?, page: Int?): Search? {
        val p = page ?: 1
        if (id == null) return null
        return tmdbRepository.getMovieRecommendations(id, p).toSearch(false)
    }

    fun getSeriesRecommendations(id: Int?, page: Int?): Search? {
        val p = page ?: 1
        if (id == null) return null
        return tmdbRepository.getSerieRecommendations(id, p).toSearch(false)
    }
}