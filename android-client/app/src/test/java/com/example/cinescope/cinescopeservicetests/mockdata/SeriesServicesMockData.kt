package com.example.cinescope.cinescopeservicetests.mockdata

import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.domain.content.EpisodeData
import com.example.cinescope.domain.content.ListId
import com.example.cinescope.domain.content.SeriesData
import com.example.cinescope.domain.content.UserDataContent
import com.example.cinescope.services.dtosMapping.ListEpisodeData
import com.example.cinescope.services.dtosMapping.ListOfContentList
import com.example.cinescope.services.dtosMapping.ListOfUserDataContent
import com.example.cinescope.services.dtosMapping.ListSeriesData

/** Simple Data **/
const val fakeUserToken = "1904-02-28"
const val fakeSeriesId = 1
const val fakeSeriesListId = 2
const val fakeSeriesListName = "Some Name"
const val fakeSeasonNr = 10
const val fakeEpisodeNr = 3
const val fakeSeriesState = "Some State"

const val episodeListId = 38

val seriesData1 = SeriesData(fakeSeriesId, "123", "Series1", "imgpath1", episodeListId, fakeSeriesState)
val seriesData2 = SeriesData(fakeSeriesId, "123", "Series1", "imgpath1", episodeListId, fakeSeriesState)

val epData1 = EpisodeData(1,1, "39", "Episode5", "imgPath", 10, 5)
val epData2 = EpisodeData(1,1, "39", "Episode6", "imgPath", 10, 6)

val seriesList1 = ContentList(1, "Action Series")
val seriesList2 = ContentList(2, "Comedy Series")

val listOfSeries1 = listOf(seriesList1)
val listOfSeries2 = listOf(seriesList1, seriesList2)

val userDataForSeriesData1 = UserDataContent(77, fakeSeriesState, listOfSeries2)

val userDataForSeriesData2 = UserDataContent(88, fakeSeriesState, listOfSeries1)

/** Responses to enqueue in MockServer **/

val getAllSeriesByStateResponse = ListSeriesData(listOf(seriesData1, seriesData2))

val getAllSeriesListsResponse = ListOfContentList(listOf(seriesList1, seriesList2))

val getSeriesListResponse = ListSeriesData(listOf(seriesData1, seriesData2))

val createSeriesListResponse = ListId(fakeSeriesId)

val getSeriesUserDataResponse = ListOfUserDataContent(listOf(userDataForSeriesData2, userDataForSeriesData1))

val getAllWatchedEpFromSeriesResponse = ListEpisodeData(listOf(epData1, epData2))

/** Expected results from SearchService methods **/

val expectedGetAllSeriesByState = listOf(seriesData1, seriesData2)

val expectedGetAllSeriesLists = listOf(seriesList1, seriesList2)

val expectedGetSeriesList = listOf(seriesData1, seriesData2)

val expectedCreateSeriesList = ListId(fakeSeriesId)

val expectedGetSeriesUserData = listOf(userDataForSeriesData2, userDataForSeriesData1)

val expectedGetAllWatchedEpFromSeries = listOf(epData1, epData2)