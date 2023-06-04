package pt.isel.ps.cinescope.controllers

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.function.ServerRequest.Headers
import pt.isel.ps.cinescope.controllers.models.ListOutput
import pt.isel.ps.cinescope.controllers.models.MoviesModel
import pt.isel.ps.cinescope.services.MoviesServices

@RestController
class MoviesController(val moviesServices: MoviesServices) {

    @PostMapping(Movies.ADD_MOVIE)
    fun addMovie(@PathVariable id: Int, @PathVariable lid: Int, @RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String): ResponseEntity<*>{
        val movie = moviesServices.addMovieToList(id,lid, bearer)
        return ResponseEntity
            .status(200)
            .body(movie)
    }

    @PostMapping(Movies.CHANGE_STATE)
    fun changeMovieState(@PathVariable id: Int, @RequestBody info: MoviesModel.ChangeStateModel, @RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String): ResponseEntity<*>{
        val movie = moviesServices.changeState(movieId = id, info.state, bearer)

        return ResponseEntity
            .status(200)
            .body(movie)
    }

    @GetMapping(Movies.GET_LIST_BY_STATE)
    fun getListsByState(@PathVariable state: String, @RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String):ResponseEntity<*>{
        val lists = moviesServices.getMoviesFromUserByState(bearer, state)

        return ResponseEntity
            .status(200)
            .body(ListOutput(lists))
    }


    @GetMapping(Movies.GET_MOVIES_LISTS)
    fun getMoviesLists(@RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String):ResponseEntity<*>{
        val lists = moviesServices.getLists(bearer)

        return ResponseEntity
            .status(200)
            .body(ListOutput(lists))
    }

    @GetMapping(Movies.GET_LIST)
    fun getMoviesList(@PathVariable id: Int, @RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String): ResponseEntity<*>{
        val list = moviesServices.getList(id, bearer)

        return ResponseEntity
            .status(200)
            .body(ListOutput(list))
    }

    @PostMapping(Movies.CREATE_LIST)
    fun createMoviesList(@RequestBody info: MoviesModel.ListModel, @RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String): ResponseEntity<*>{
        val list = moviesServices.createList(bearer, info.name)

        return ResponseEntity
            .status(200)
            .body(list)
    }

    @DeleteMapping(Movies.DELETE_LIST)
    fun deleteMoviesList(@PathVariable id: Int, @RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String): ResponseEntity<*>{
        val list = moviesServices.deleteList(id, bearer)

        return ResponseEntity
            .status(200)
            .body(list)
    }

    @DeleteMapping(Movies.DELETE_MOVIE_FROM_LIST)
    fun deleteMovieFromList(@PathVariable id: Int, @PathVariable mid: Int?, @RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String): ResponseEntity<*>{
        val list = moviesServices.deleteMovieFromList(id, movieId = mid, bearer)

        return ResponseEntity
            .status(200)
            .body(list)
    }

    @DeleteMapping(Movies.REMOVE_MOVIE_STATE)
    fun deleteStateFromMovie(@PathVariable mid: Int, @RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String): ResponseEntity<*>{
        return ResponseEntity
            .status(200)
            .body(moviesServices.deleteStateFromMovie(mid, bearer))
    }

    @GetMapping(Movies.MOVIE_USER_DATA)
    fun getMovieUserData(@PathVariable id: Int, @RequestHeader(HttpHeaders.AUTHORIZATION) bearer: String): ResponseEntity<*>{
        val movieUserData = moviesServices.getMovieUserData(id, bearer)
        return ResponseEntity
            .status(200)
            .body(movieUserData)
    }
}