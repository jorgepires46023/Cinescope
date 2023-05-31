package pt.isel.ps.cinescope.controllers

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ps.cinescope.controllers.models.SeriesModel
import pt.isel.ps.cinescope.services.SeriesServices

@RestController
class SeriesController(val seriesServices: SeriesServices) {

    @PostMapping(Series.ADD_SERIE)
    fun addSeries(@PathVariable id: Int, @PathVariable lid: Int, @RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String): ResponseEntity<*> {
        val serie = seriesServices.addSeriesToList(tmdbSeriesId = id, listId = lid, bearer)

        return ResponseEntity
            .status(200)
            .body(serie)
    }

    @PostMapping(Series.CHANGE_STATE)
    fun changeSeriesState(@PathVariable id: Int, @RequestBody info: SeriesModel.StateModel, @RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String): ResponseEntity<*>{
        val serie = seriesServices.changeState(seriesId = id, info.state, bearer)

        return ResponseEntity
            .status(200)
            .body(serie)
    }

    @GetMapping(Series.GET_SERIES_BY_STATE)
    fun getSeriesByState(@RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String, @PathVariable state: String): ResponseEntity<*>{
        val series = seriesServices.getSeriesFromUserByState(bearer, state)

        return ResponseEntity
            .status(200)
            .body(series)
    }

    @PostMapping(Series.ADD_WATCHED_EP)
    fun addWatchedEpisode(@PathVariable id: Int, @RequestBody info: SeriesModel.EpisodeModel, @RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String): ResponseEntity<*>{
        val episode = seriesServices.addWatchedEpisode(seriesId = id, info.episodeNumber, info.seasonNumber, bearer)

        return ResponseEntity
            .status(200)
            .body(episode)
    }

    @DeleteMapping(Series.REMOVE_WATCHED_EP)
    fun removeWatchedEpisode(@PathVariable id: Int, @PathVariable season: Int, @PathVariable ep: Int, @RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String): ResponseEntity<*>{
        val episode = seriesServices.removeWatchedEpisode(seriesId = id, ep, season, bearer)

        return ResponseEntity
            .status(200)
            .body(episode)
    }

    @GetMapping(Series.GET_WATCHED_EP_LIST)
    fun getWatchedEpList(@PathVariable id: Int, @RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String): ResponseEntity<*>{
        val list = seriesServices.getWatchedEpList(seriesId = id, bearer)

        return ResponseEntity
            .status(200)
            .body(list)
    }


    @GetMapping(Series.GET_SERIES_LISTS)
    fun getSeriesLists(@RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String):ResponseEntity<*>{
        val lists = seriesServices.getLists(bearer)

        return ResponseEntity
            .status(200)
            .body(lists)
    }

    @GetMapping(Series.GET_LIST)
    fun getSeriesList(@PathVariable id: Int, @RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String): ResponseEntity<*>{
        val list = seriesServices.getList(listId = id, bearer)

        return ResponseEntity
            .status(200)
            .body(list)
    }

    @PostMapping(Series.CREATE_LIST)
    fun createSeriesList(@RequestBody info: SeriesModel.ListModel,@RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String): ResponseEntity<*>{
        val list = seriesServices.createList(bearer, info.name)

        return ResponseEntity
            .status(200)
            .body(list)
    }

    @DeleteMapping(Series.DELETE_LIST)
    fun deleteSeriesList(@PathVariable id: Int, @RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String): ResponseEntity<*>{
        val list = seriesServices.deleteList(listId = id, bearer)

        return ResponseEntity
            .status(200)
            .body(list)
    }

    @DeleteMapping(Series.DELETE_SERIE_FROM_LIST)
    fun deleteSerieFromList(@PathVariable id: Int, @PathVariable sid: Int, @RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String): ResponseEntity<*>{
        val list = seriesServices.deleteSeriesFromList(listId = id, seriesId =  sid, bearer)

        return ResponseEntity
            .status(200)
            .body(list)
    }

    @DeleteMapping(Series.REMOVE_SERIE_STATE)
    fun removeSerieState(@PathVariable id: Int, @RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String): ResponseEntity<*>{
        val serie = seriesServices.removeStateFromSerie(id, bearer)

        return ResponseEntity
            .status(200)
            .body(serie)
    }

    @GetMapping(Series.SERIE_USER_DATA)
    fun getSerieUserData(@PathVariable id: Int, @RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String): ResponseEntity<*>{
        val serieUserData = seriesServices.getSerieUserData(id, bearer)

        return ResponseEntity
            .status(200)
            .body(serieUserData)
    }

}