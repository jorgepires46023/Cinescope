package com.example.cinescope.services.cinescopeAPI

import com.example.cinescope.domain.searches.EpisodeInfo
import com.example.cinescope.domain.searches.MediaContent
import com.example.cinescope.domain.searches.MovieInfo
import com.example.cinescope.domain.searches.SeasonInfo
import com.example.cinescope.domain.searches.SeriesInfo
import com.example.cinescope.services.dtosMapping.ContentAPIDto
import com.example.cinescope.services.dtosMapping.toContent
import com.example.cinescope.services.serviceInterfaces.CinescopeSearchServices
import com.example.cinescope.utils.joinPath
import com.example.cinescope.utils.joinPathWithVariables
import com.example.cinescope.utils.send
import com.google.gson.Gson
import okhttp3.OkHttpClient
import java.net.URL

class SearchServices(
    private val cinescopeURL: URL,
    gson: Gson,
    httpClient: OkHttpClient
) : CinescopeSearchServices, CinescopeServices(gson, httpClient) {
    override suspend fun searchByQuery(searchQuery: String): List<MediaContent> {
            val request = buildRequest(
                url = cinescopeURL
                    .joinPathWithVariables(Searches.SEARCH_QUERY, listOf(searchQuery))
            )
            //TODO handle this exceptions with our errors(try-catch)
            val contentAPIDto = httpClient.send(request){ response ->
                handleResponse<ContentAPIDto>(response, ContentAPIDto::class.java)
            }
            val res = contentAPIDto.toContent()
            return res
    }

    override suspend fun getPopularMovies(): List<MediaContent> {
        val request = buildRequest(
            url = cinescopeURL.joinPath(Searches.GET_POPULAR_MOVIES)
        )
        //TODO handle this exceptions with our errors(try-catch)
        val contentAPIDto = httpClient.send(request){ response ->
            handleResponse<ContentAPIDto>(response, ContentAPIDto::class.java)
        }
        return contentAPIDto.toContent()
    }

    override suspend fun getPopularSeries(): List<MediaContent> {
        val request = buildRequest(
            url = cinescopeURL.joinPath(Searches.GET_POPULAR_SERIES)
        )
        //TODO handle this exceptions with our errors(try-catch)
        val contentAPIDto = httpClient.send(request){ response ->
            handleResponse<ContentAPIDto>(response, ContentAPIDto::class.java)
        }
        return contentAPIDto.toContent()
    }

    override suspend fun getMovieDetails(movieId: Int): MovieInfo {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Searches.MOVIE_DETAILS, listOf(movieId.toString()))
        )
        //TODO handle this exceptions with our errors(try-catch)
        return httpClient.send(request){ response ->
            handleResponse(response, MovieInfo::class.java)
        }
    }

    override suspend fun getSeriesDetails(seriesId: Int): SeriesInfo {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Searches.SERIE_DETAILS, listOf(seriesId.toString()))
        )
        return httpClient.send(request){ response ->
            handleResponse(response, SeriesInfo::class.java)
        }
    }

    override suspend fun getSeasonDetails(seriesId: Int, seasonNr: Int): SeasonInfo {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Searches.SEASON_DETAILS, listOf(seriesId.toString(), seasonNr.toString()))
        )
        //TODO handle this exceptions with our errors(try-catch)
        return httpClient.send(request){ response ->
            handleResponse(response, SeasonInfo::class.java)
        }
    }

    override suspend fun getEpisodeDetails(seriesId: Int, seasonNr: Int, epNumber: Int): EpisodeInfo {
        val request = buildRequest(
            url = cinescopeURL
                .joinPathWithVariables(Searches.EPISODE_DETAILS,
                    listOf(seriesId.toString(),seasonNr.toString(), epNumber.toString()))
        )
        //TODO handle this exceptions with our errors(try-catch)
        return httpClient.send(request){ response ->
            handleResponse(response, EpisodeInfo::class.java)
        }
    }


}
