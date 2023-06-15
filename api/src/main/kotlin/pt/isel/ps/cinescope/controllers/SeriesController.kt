package pt.isel.ps.cinescope.controllers

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ps.cinescope.controllers.models.ListOutput
import pt.isel.ps.cinescope.controllers.models.SeriesModel
import pt.isel.ps.cinescope.services.SeriesServices

@RestController
class SeriesController(val seriesServices: SeriesServices) {

    @PostMapping(Series.ADD_SERIE)
    fun addSeries(@PathVariable id: Int, @PathVariable lid: Int, @CookieValue(value = "userToken") cookie: String): ResponseEntity<*> {
        val serie = seriesServices.addSeriesToList(tmdbSeriesId = id, listId = lid, cookie)

        return ResponseEntity
            .status(200)
            .body(serie)
    }

    @PostMapping(Series.CHANGE_STATE)
    fun changeSeriesState(@PathVariable id: Int, @RequestBody info: SeriesModel.StateModel, @CookieValue(value = "userToken") cookie: String): ResponseEntity<*>{
        val serie = seriesServices.changeState(seriesId = id, info.state, cookie)

        return ResponseEntity
            .status(200)
            .body(serie)
    }

    @GetMapping(Series.GET_SERIES_BY_STATE)
    fun getSeriesByState(@CookieValue(value = "userToken") cookie: String, @PathVariable state: String): ResponseEntity<*>{
        val series = seriesServices.getSeriesFromUserByState(cookie, state)
        return ResponseEntity
            .status(200)
            .body(ListOutput(series))
    }

    @PostMapping(Series.ADD_WATCHED_EP)
    fun addWatchedEpisode(@PathVariable id: Int, @RequestBody info: SeriesModel.EpisodeModel, @CookieValue(value = "userToken") cookie: String): ResponseEntity<*>{
        val episode = seriesServices.addWatchedEpisode(seriesId = id, info.episodeNumber, info.seasonNumber, cookie)

        return ResponseEntity
            .status(200)
            .body(episode)
    }

    @DeleteMapping(Series.REMOVE_WATCHED_EP)
    fun removeWatchedEpisode(@PathVariable id: Int, @PathVariable season: Int, @PathVariable ep: Int, @CookieValue(value = "userToken") cookie: String): ResponseEntity<*>{
        val episode = seriesServices.removeWatchedEpisode(seriesId = id, ep, season, cookie)

        return ResponseEntity
            .status(200)
            .body(episode)
    }

    @GetMapping(Series.GET_WATCHED_EP_LIST)
    fun getWatchedEpList(@PathVariable id: Int, @CookieValue(value = "userToken") cookie: String): ResponseEntity<*>{
        val list = seriesServices.getWatchedEpList(seriesId = id, cookie)

        return ResponseEntity
            .status(200)
            .body(ListOutput(list))
    }


    @GetMapping(Series.GET_SERIES_LISTS)
    fun getSeriesLists(@CookieValue(value = "userToken") cookie: String):ResponseEntity<*>{
        val lists = seriesServices.getLists(cookie)

        return ResponseEntity
            .status(200)
            .body(ListOutput(lists))
    }

    @GetMapping(Series.GET_LIST)
    fun getSeriesList(@PathVariable id: Int, @CookieValue(value = "userToken") cookie: String): ResponseEntity<*>{
        val list = seriesServices.getList(listId = id, cookie)

        return ResponseEntity
            .status(200)
            .body(ListOutput(list))
    }

    @PostMapping(Series.CREATE_LIST)
    fun createSeriesList(@RequestBody info: SeriesModel.ListModel,@CookieValue(value = "userToken") cookie: String): ResponseEntity<*>{
        val list = seriesServices.createList(cookie, info.name)

        return ResponseEntity
            .status(200)
            .body(list)
    }

    @DeleteMapping(Series.DELETE_LIST)
    fun deleteSeriesList(@PathVariable id: Int, @CookieValue(value = "userToken") cookie: String): ResponseEntity<*>{
        val list = seriesServices.deleteList(listId = id, cookie)

        return ResponseEntity
            .status(200)
            .body(list)
    }

    @DeleteMapping(Series.DELETE_SERIE_FROM_LIST)
    fun deleteSerieFromList(@PathVariable id: Int, @PathVariable sid: Int, @CookieValue(value = "userToken") cookie: String): ResponseEntity<*>{
        val list = seriesServices.deleteSeriesFromList(listId = id, seriesId =  sid, cookie)

        return ResponseEntity
            .status(200)
            .body(list)
    }

    @DeleteMapping(Series.REMOVE_SERIE_STATE)
    fun removeSerieState(@PathVariable id: Int, @CookieValue(value = "userToken") cookie: String): ResponseEntity<*>{
        val serie = seriesServices.removeStateFromSerie(id, cookie)

        return ResponseEntity
            .status(200)
            .body(serie)
    }

    @GetMapping(Series.SERIE_USER_DATA)
    fun getSerieUserData(@PathVariable id: Int, @CookieValue(value = "userToken") cookie: String): ResponseEntity<*>{
        val serieUserData = seriesServices.getSerieUserData(id, cookie)

        return ResponseEntity
            .status(200)
            .body(serieUserData)
    }

}