package com.example.cinescope.cinescopeservicetests.mockdata

import com.example.cinescope.domain.MovieState
import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.domain.content.ListId
import com.example.cinescope.domain.content.MovieData
import com.example.cinescope.domain.content.UserDataContent
import com.example.cinescope.services.dtosMapping.ListMovieData
import com.example.cinescope.services.dtosMapping.ListOfContentList
import com.example.cinescope.services.dtosMapping.ListOfUserDataContent

/** Simple Data **/
const val fakeToken = "1904-02-28"
const val fakeMovieId = 1
const val fakeListId = 2
const val fakeListName = "Some Name"
const val fakeMovieState = "SomeState"

val movieData1 = MovieData(99, "123", "imgpath1", "Movie1", fakeMovieState)
val movieData2 = MovieData(88, "456", "imgpath2", "Movie2", fakeMovieState)
val movieData3 = MovieData(77, "789", "imgpath3", "Movie3", fakeMovieState)

val movieList1 = ContentList(1, "Action Movies")
val movieList2 = ContentList(2, "Comedy Movies")
val movieList3 = ContentList(3, "Drama Movies")

val listId1 = ListId(1)

val listWhichContainMovieData2 = listOf(movieList1, movieList2)

val listWhichContainMovieData3 = listOf(movieList3)

val userDataForMovieData2 = UserDataContent(88, MovieState.WATCHED.state, listWhichContainMovieData2)

val userDataForMovieData3 = UserDataContent(77, MovieState.PTW.state, listWhichContainMovieData3)

/** Responses to enqueue in MockServer **/

val getAllMoviesByStateResponse = ListMovieData(listOf(movieData1, movieData2)) //When state = "Watched"

val getAllMoviesListsResponse = ListOfContentList(listOf(movieList1, movieList2, movieList3))

val getMoviesListResponse = ListMovieData(listOf(movieData3, movieData2))

val createMoviesListResponse = listId1

val getMovieUserDataResponse = ListOfUserDataContent(listOf(userDataForMovieData2, userDataForMovieData3))

/** Expected results from SearchService methods **/

val expectedGetAllMoviesByState = listOf(movieData1, movieData2) //When state = "Watched"

val expectedGetAllMoviesLists = listOf(movieList1, movieList2, movieList3)

val expectedGetMoviesList = listOf(movieData3, movieData2)

val expectedCreateMoviesList = listId1

val expectedGetMovieUserData = listOf(userDataForMovieData2, userDataForMovieData3)
