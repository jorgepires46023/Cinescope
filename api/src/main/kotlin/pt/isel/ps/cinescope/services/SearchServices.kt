package pt.isel.ps.cinescope.services

import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.domain.*
import pt.isel.ps.cinescope.utils.TmdbService


@Component
class SearchServices(val tmdbServices: TmdbService) {

    fun searchByQuery(input: String?): Search? {
        if (input.isNullOrBlank()) return null
        return tmdbServices.fetchQuery(input)
    }

    fun serieDetails(id: Int?): SeriesDetailsOutput?{
        if (id == null) return null
        val seriesDetails = tmdbServices.getSerieDetails(id) ?: return null
        val watchProviders = tmdbServices.getSeriesWatchProviders(id) ?: return null
        return SeriesDetailsOutput(seriesDetails, watchProviders)
    }

    fun seasonDetails(id: Int?, seasonNum: Int?): SeasonDetails?{
        if(id == null || seasonNum == null) return null
        return tmdbServices.getSeasonDetails(id, seasonNum)
    }

    fun episodeDetails(id: Int?, seasonNum: Int?, epNum: Int?): EpisodeDetails?{
        if(id == null || seasonNum == null || epNum == null) return null
        return tmdbServices.getEpisodeDetails(id, seasonNum, epNum)
    }

}