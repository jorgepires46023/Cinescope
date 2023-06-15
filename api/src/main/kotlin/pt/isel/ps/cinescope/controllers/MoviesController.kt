package pt.isel.ps.cinescope.controllers

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ps.cinescope.controllers.models.ListOutput
import pt.isel.ps.cinescope.controllers.models.MoviesModel
import pt.isel.ps.cinescope.services.MoviesServices

@RestController
class MoviesController(val moviesServices: MoviesServices) {

    @PostMapping(Movies.ADD_MOVIE)
    fun addMovie(@PathVariable id: Int, @PathVariable lid: Int, @CookieValue(value = "userToken") cookie: String): ResponseEntity<*>{
        val movie = moviesServices.addMovieToList(id,lid, cookie)
        return ResponseEntity
            .status(200)
            .body(movie)
    }

    @PostMapping(Movies.CHANGE_STATE)
    fun changeMovieState(@PathVariable id: Int, @RequestBody info: MoviesModel.ChangeStateModel, @CookieValue(value = "userToken") cookie: String): ResponseEntity<*>{
        val movie = moviesServices.changeState(movieId = id, info.state, cookie)

        return ResponseEntity
            .status(200)
            .body(movie)
    }

    @GetMapping(Movies.GET_LIST_BY_STATE)
    fun getListsByState(@PathVariable state: String, @CookieValue(value = "userToken") cookie: String):ResponseEntity<*>{
        val lists = moviesServices.getMoviesFromUserByState(cookie, state)

        return ResponseEntity
            .status(200)
            .body(ListOutput(lists))
    }


    @GetMapping(Movies.GET_MOVIES_LISTS)
    fun getMoviesLists(@CookieValue(value = "userToken") cookie: String):ResponseEntity<*>{
        val lists = moviesServices.getLists(cookie)

        return ResponseEntity
            .status(200)
            .body(ListOutput(lists))
    }

    @GetMapping(Movies.GET_LIST)
    fun getMoviesList(@PathVariable id: Int, @CookieValue(value = "userToken") cookie: String): ResponseEntity<*>{
        val list = moviesServices.getList(id, cookie)

        return ResponseEntity
            .status(200)
            .body(ListOutput(list))
    }

    @PostMapping(Movies.CREATE_LIST)
    fun createMoviesList(@RequestBody info: MoviesModel.ListModel, @CookieValue(value = "userToken") cookie: String): ResponseEntity<*>{
        val list = moviesServices.createList(cookie, info.name)

        return ResponseEntity
            .status(200)
            .body(list)
    }

    @DeleteMapping(Movies.DELETE_LIST)
    fun deleteMoviesList(@PathVariable id: Int, @CookieValue(value = "userToken") cookie: String): ResponseEntity<*>{
        val list = moviesServices.deleteList(id, cookie)

        return ResponseEntity
            .status(200)
            .body(list)
    }

    @DeleteMapping(Movies.DELETE_MOVIE_FROM_LIST)
    fun deleteMovieFromList(@PathVariable id: Int, @PathVariable mid: Int?, @CookieValue(value = "userToken") cookie: String): ResponseEntity<*>{
        val list = moviesServices.deleteMovieFromList(id, movieId = mid, cookie)

        return ResponseEntity
            .status(200)
            .body(list)
    }

    @DeleteMapping(Movies.REMOVE_MOVIE_STATE)
    fun deleteStateFromMovie(@PathVariable mid: Int, @CookieValue(value = "userToken") cookie: String): ResponseEntity<*>{
        return ResponseEntity
            .status(200)
            .body(moviesServices.deleteStateFromMovie(mid, cookie))
    }

    @GetMapping(Movies.MOVIE_USER_DATA)
    fun getMovieUserData(@PathVariable id: Int, @CookieValue(value = "userToken") cookie: String): ResponseEntity<*>{
        val movieUserData = moviesServices.getMovieUserData(id, cookie)
        return ResponseEntity
            .status(200)
            .body(movieUserData)
    }
}