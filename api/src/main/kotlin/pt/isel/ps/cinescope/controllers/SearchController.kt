package pt.isel.ps.cinescope.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ps.cinescope.services.SearchServices

@RestController
class SearchController(val searchServices: SearchServices) {
    @GetMapping(Searches.SEARCH_QUERIE)
    fun searchByQuery(@PathVariable query: String, @RequestParam page: Int?):ResponseEntity<*>{
        val search = searchServices.searchByQuery(input = query, page = page)
        return ResponseEntity
            .status(200)
            .body(search)
    }

    @GetMapping(Searches.MOVIE_DETAILS)
    fun movieDetails(@PathVariable id: Int): ResponseEntity<*>{
        val movieDetails = searchServices.movieDetails(id = id)
        return ResponseEntity
            .status(200)
            .body(movieDetails)
    }

    @GetMapping(Searches.SERIE_DETAILS)
    fun serieDetails(@PathVariable id: Int): ResponseEntity<*>{
        val serieDetails = searchServices.seriesDetails(id = id)
        return ResponseEntity
            .status(200)
            .body(serieDetails)
    }

    @GetMapping(Searches.SEASON_DETAILS)
    fun seasonDetails(@PathVariable id: Int, @PathVariable seasonnum: Int): ResponseEntity<*>{
        val seasonDetails = searchServices.seasonDetails(id = id, seasonNum = seasonnum)
        return ResponseEntity
            .status(200)
            .body(seasonDetails)
    }

    @GetMapping(Searches.EPISODE_DETAILS)
    fun episodeDetails(@PathVariable id: Int, @PathVariable seasonnum: Int, @PathVariable epnum: Int): ResponseEntity<*>{
        val epDetails = searchServices.episodeDetails(id = id, seasonNum = seasonnum, epNum = epnum)
        return ResponseEntity
            .status(200)
            .body(epDetails)
    }

    @GetMapping(Searches.GET_POPULAR_MOVIES)
    fun getPopularMovies(@RequestParam page: Int?): ResponseEntity<*>{
        return ResponseEntity
            .status(200)
            .body(searchServices.getPopularMovies(page = page))
    }

    @GetMapping(Searches.GET_POPULAR_SERIES)
    fun getPopularSeries(@RequestParam page: Int?): ResponseEntity<*>{
        return ResponseEntity
            .status(200)
            .body(searchServices.getPopularSeries(page = page))
    }

    @GetMapping(Searches.MOVIE_RECOMMENDATIONS)
    fun getMovieRecommendations(@PathVariable id: Int, @RequestParam page: Int?): ResponseEntity<*>{
        return ResponseEntity
            .status(200)
            .body(searchServices.getMovieRecommendations(id = id, page = page))
    }

    @GetMapping(Searches.SERIE_RECOMMENDATIONS)
    fun getSeriesRecommendations(@PathVariable id: Int, @RequestParam page: Int?): ResponseEntity<*>{
        return ResponseEntity
            .status(200)
            .body(searchServices.getSeriesRecommendations(id = id, page = page))
    }
}