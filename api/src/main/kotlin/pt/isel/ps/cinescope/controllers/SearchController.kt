package pt.isel.ps.cinescope.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec
import pt.isel.ps.cinescope.services.SearchServices

@RestController
class SearchController(val searchServices: SearchServices) {
    @GetMapping(Searches.SEARCH_QUERIE)
    fun searchByQuery(@PathVariable query: String):ResponseEntity<*>{
        val search = searchServices.searchByQuery(query)

        return ResponseEntity
            .status(200)
            .body(search)
    }

    @GetMapping(Searches.MOVIE_DETAILS)
    fun movieDetails(@PathVariable id: Int): ResponseEntity<*>{
        val movieDetails = searchServices.movieDetails(id)

        return ResponseEntity
            .status(200)
            .body(movieDetails)
    }

    @GetMapping(Searches.SERIE_DETAILS)
    fun serieDetails(@PathVariable id: Int): ResponseEntity<*>{
        val serieDetails = searchServices.serieDetails(id)
        return ResponseEntity
            .status(200)
            .body(serieDetails)
    }

    @GetMapping(Searches.SEASON_DETAILS)
    fun seasonDetails(@PathVariable id: Int, @PathVariable seasonnum: Int): ResponseEntity<*>{
        val seasonDetails = searchServices.seasonDetails(id, seasonnum)

        return ResponseEntity
            .status(200)
            .body(seasonDetails)
    }

    @GetMapping(Searches.EPISODE_DETAILS)
    fun episodeDetails(@PathVariable id: Int, @PathVariable seasonnum: Int, @PathVariable epnum: Int): ResponseEntity<*>{
        val epDetails = searchServices.episodeDetails(id, seasonnum, epnum)

        return ResponseEntity
            .status(200)
            .body(epDetails)
    }
}