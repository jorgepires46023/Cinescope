package pt.isel.ps.cinescope.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ps.cinescope.controllers.models.ListOutput
import pt.isel.ps.cinescope.controllers.models.MoviesModel
import pt.isel.ps.cinescope.services.MoviesServices

@RestController
class MoviesController(val moviesServices: MoviesServices) {

    @PostMapping(Movies.ADD_MOVIE)
    fun addMovie(@PathVariable id: Int, @PathVariable lid: Int, @CookieValue(value = "userToken") token: String): ResponseEntity<*>{
        val movie = moviesServices.addMovieToList(tmdbMovieId = id, listId = lid, token = token)
        return ResponseEntity
            .status(200)
            .body(movie)
    }

    @PostMapping(Movies.CHANGE_STATE)
    fun changeMovieState(@PathVariable id: Int, @RequestBody info: MoviesModel.ChangeStateModel, @CookieValue(value = "userToken") token: String): ResponseEntity<*>{
        val movie = moviesServices.changeState(movieId = id, state = info.state, token = token)
        return ResponseEntity
            .status(200)
            .body(movie)
    }

    @GetMapping(Movies.GET_LIST_BY_STATE)
    fun getListsByState(@PathVariable state: String, @CookieValue(value = "userToken") token: String):ResponseEntity<*>{
        val lists = moviesServices.getMoviesFromUserByState(token = token, state = state)
        return ResponseEntity
            .status(200)
            .body(ListOutput(lists))
    }


    @GetMapping(Movies.GET_MOVIES_LISTS)
    fun getMoviesLists(@CookieValue(value = "userToken") token: String):ResponseEntity<*>{
        val lists = moviesServices.getLists(token = token)
        return ResponseEntity
            .status(200)
            .body(ListOutput(lists))
    }

    @GetMapping(Movies.GET_LIST)
    fun getMoviesList(@PathVariable id: Int, @CookieValue(value = "userToken") token: String): ResponseEntity<*>{
        val list = moviesServices.getList(listId = id, token = token)
        return ResponseEntity
            .status(200)
            .body(list)
    }

    @PostMapping(Movies.CREATE_LIST)
    fun createMoviesList(@RequestBody info: MoviesModel.ListModel, @CookieValue(value = "userToken") token: String): ResponseEntity<*>{
        val list = moviesServices.createList(token = token, name = info.name)
        return ResponseEntity
            .status(200)
            .body(list)
    }

    @DeleteMapping(Movies.DELETE_LIST)
    fun deleteMoviesList(@PathVariable id: Int, @CookieValue(value = "userToken") token: String): ResponseEntity<*>{
        val list = moviesServices.deleteList(listId = id, token = token)
        return ResponseEntity
            .status(200)
            .body(list)
    }

    @DeleteMapping(Movies.DELETE_MOVIE_FROM_LIST)
    fun deleteMovieFromList(@PathVariable id: Int, @PathVariable mid: Int?, @CookieValue(value = "userToken") token: String): ResponseEntity<*>{
        val list = moviesServices.deleteMovieFromList(listId = id, movieId = mid, token = token)
        return ResponseEntity
            .status(200)
            .body(list)
    }

    @DeleteMapping(Movies.REMOVE_MOVIE_STATE)
    fun deleteStateFromMovie(@PathVariable mid: Int, @CookieValue(value = "userToken") token: String): ResponseEntity<*>{
        return ResponseEntity
            .status(200)
            .body(moviesServices.deleteStateFromMovie(movieId = mid, token = token))
    }

    @GetMapping(Movies.MOVIE_USER_DATA)
    fun getMovieUserData(@PathVariable id: Int, @CookieValue(value = "userToken") token: String): ResponseEntity<*>{
        val movieUserData = moviesServices.getMovieUserData(movieId = id, token = token)
        return ResponseEntity
            .status(200)
            .body(movieUserData)
    }
}