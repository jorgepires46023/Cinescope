package com.example.cinescope.services.dtosMapping

import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.domain.content.MovieData
import com.example.cinescope.domain.content.UserDataContent

data class ListMovieData(val list: List<MovieData>)

data class ListOfContentList(val list: List<ContentList>)

data class ListOfUserDataContent(val list: List<UserDataContent>)
