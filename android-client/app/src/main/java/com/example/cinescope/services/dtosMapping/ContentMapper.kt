package com.example.cinescope.services.dtosMapping

import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.domain.content.EpisodeData
import com.example.cinescope.domain.content.MovieData
import com.example.cinescope.domain.content.SeriesData
import com.example.cinescope.domain.content.UserDataContent

data class ListMovieData(val results: List<MovieData>)

data class ListSeriesData(val results: List<SeriesData>)

data class ListEpisodeData(val results: List<EpisodeData>)

data class ListOfContentList(val results: List<ContentList>)

data class ListOfUserDataContent(val results: List<UserDataContent>)
