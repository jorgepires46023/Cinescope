package com.example.cinescope.domain.content

import com.example.cinescope.domain.searches.SeasonInfo

data class SeasonDataState(
    val seasonNr: Int,
    val seasonInfo: SeasonInfo
)