package pt.isel.ps.cinescope.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ps.cinescope.controllers.models.SeriesModel
import pt.isel.ps.cinescope.services.SeriesServices

@RestController
class SeriesController(val seriesServices: SeriesServices) {

    @PostMapping(Series.ADD_SERIE)
    fun addSeries(@PathVariable id: String, @RequestBody info: SeriesModel.AddInputModel): ResponseEntity<*> {
        val serie = seriesServices.addSeriesToList(seriesId = id, info.listid, info.userid)

        return ResponseEntity
            .status(200)
            .body(serie)
    }

    @PostMapping(Series.CHANGE_STATE)
    fun changeSeriesState(@PathVariable id: String, @RequestBody info: SeriesModel.ChangeStateModel): ResponseEntity<*>{
        val serie = seriesServices.changeState(seriesId = id, info.state, info.userid)

        return ResponseEntity
            .status(200)
            .body(serie)
    }

    @PostMapping(Series.ADD_WATCHED_EP)
    fun addWatchedEpisode(@PathVariable id: String, @PathVariable epid: String, @RequestBody info: SeriesModel.EpisodeModel): ResponseEntity<*>{
        val episode = seriesServices.addWatchedEpisode(seriesId = id, epid, info.userid)

        return ResponseEntity
            .status(200)
            .body(episode)
    }

    //@PostMapping(Series.REMOVE_WATCHED_EP)
    fun removeWatchedEpisode(@PathVariable id: String, @PathVariable epid: String, @RequestBody info: SeriesModel.EpisodeModel): ResponseEntity<*>{
        val episode = seriesServices.removeWatchedEpisode(seriesId = id, epid, info.userid)

        return ResponseEntity
            .status(200)
            .body(episode)
    }

    @GetMapping(Series.GET_WATCHED_EP_LIST)
    fun getWatchedEpList(@PathVariable id: String, @RequestBody info: SeriesModel.EpisodeModel): ResponseEntity<*>{
        val list = seriesServices.getWatchedEpList(id, info.userid)

        return ResponseEntity
            .status(200)
            .body(list)
    }


    @GetMapping(Series.GET_SERIES_LISTS)
    fun getSeriesLists(@RequestBody info: SeriesModel.ListModel):ResponseEntity<*>{
        val lists = seriesServices.getLists(info.userid)

        return ResponseEntity
            .status(200)
            .body(lists)
    }

    @GetMapping(Series.GET_LIST)
    fun getSeriesList(@PathVariable id: Int, @RequestBody info: SeriesModel.ListModel): ResponseEntity<*>{
        val list = seriesServices.getList(id, info.userid)

        return ResponseEntity
            .status(200)
            .body(list)
    }

    @PostMapping(Series.CREATE_LIST)
    fun createSeriesList(@RequestBody info: SeriesModel.ListModel): ResponseEntity<*>{
        val list = seriesServices.createList(info.userid, info.name)

        return ResponseEntity
            .status(200)
            .body(list)
    }

    @PostMapping(Series.DELETE_LIST)
    fun deleteSeriesList(@PathVariable id: Int, @RequestBody info: SeriesModel.ListModel): ResponseEntity<*>{
        val list = seriesServices.deleteList(id, info.userid)

        return ResponseEntity
            .status(200)
            .body(list)
    }

    @DeleteMapping(Series.DELETE_SERIE_FROM_LIST)
    fun deleteSerieFromList(@PathVariable id: Int, @PathVariable sid: String?, @RequestBody info: SeriesModel.ListModel): ResponseEntity<*>{
        val list = seriesServices.deleteSeriesFromList(id, seriesId =  sid, info.userid)

        return ResponseEntity
            .status(200)
            .body(list)
    }

}