package com.example.cinescope.series.seriesDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.cinescope.domain.content.ContentList
import com.example.cinescope.domain.content.EpisodeData
import com.example.cinescope.domain.content.UserDataContent
import com.example.cinescope.domain.searches.Season
import com.example.cinescope.domain.searches.SeasonInfo
import com.example.cinescope.domain.searches.SeriesInfo
import com.example.cinescope.series.seriesDetails.ui.SeriesDetailsTabs
import com.example.cinescope.ui.bottombar.BottomBar
import com.example.cinescope.ui.topbar.TopBar
import com.example.cinescope.ui.bottombar.NavController
import com.example.cinescope.ui.floatingbutton.FloatingButton
import com.example.cinescope.ui.theme.CinescopeTheme

data class SeriesDetailsState(
    val series: SeriesInfo?,
    val loading: Boolean,
    val error: String?,
    val seriesData: UserDataContent?
)
data class SeriesUserData(
    val seriesData : UserDataContent?,
    val lists: List<ContentList>?,
    val onAddToList: (Int) -> Unit,
    val onDeleteFromList: (Int) -> Unit,
    val onGetLists:  () -> Unit
)

data class SeasonData(
    val seasonLists: List<Season>?,
    val watchedEpisodeList: List<EpisodeData>?,
    val seasonDetails: SeasonInfo?,
    val onGetSeasonDetails: (Int) -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeriesDetailsScreen(
    state: SeriesDetailsState,
    userData: SeriesUserData,
    navController: NavController,
    onSearchRequested: () -> Unit,
    loggedIn: Boolean,
    onChangeState: (String) -> Unit,
    onError: () -> Unit = {},
    onTabChanged: (String) -> Unit,
    onGetDetails: (Int) -> Unit,
    seasonData: SeasonData,
    onUpdate: () -> Unit
) {
    CinescopeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBar(
                    onSearchRequested = onSearchRequested
                )
            },
            floatingActionButton = {
                FloatingButton(
                    lists = userData.lists,
                    onGetLists =  userData.onGetLists,
                    onAddToList = userData.onAddToList,
                    onDeleteFromList = userData.onDeleteFromList,
                    userData = userData.seriesData,
                    onUpdate = onUpdate

                )
            },
            floatingActionButtonPosition = FabPosition.End,
            bottomBar = {
                BottomBar(
                    navController = navController
                )
            }
        ){ innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SeriesDetailsTabs(
                        onError,
                        onGetDetails,
                        onTabChanged,
                        loggedIn = loggedIn,
                        onChangeState = onChangeState,
                        state = state,
                        seasonList = seasonData.seasonLists,
                        watchedEpisodeList = seasonData.watchedEpisodeList,
                        seasonDetails = seasonData.seasonDetails,
                        onGetSeasonDetails = seasonData.onGetSeasonDetails
                    )
                }
            }
        }
    }
}