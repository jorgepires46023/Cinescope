package pt.isel.ps.cinescope.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ps.cinescope.controllers.models.MoviesModel
import pt.isel.ps.cinescope.services.MoviesServices

@RestController
class MoviesController(val moviesServices: MoviesServices) {

    @PostMapping(Movies.ADD_MOVIE)
    fun addMovie(@PathVariable id: String, @RequestBody info: MoviesModel.AddInputModel): ResponseEntity<*>{
        val movie = moviesServices.addMovieToList(movieId = id, info.listid, info.userid)

        return ResponseEntity
            .status(200)
            .body(movie)
    }

    @PostMapping(Movies.CHANGE_STATE)
    fun changeMovieState(@PathVariable id: String, @RequestBody info: MoviesModel.ChangeStateModel): ResponseEntity<*>{
        val movie = moviesServices.changeState(movieId = id, info.state, info.userid)

        return ResponseEntity
            .status(200)
            .body(movie)
    }

    @GetMapping(Movies.GET_MOVIES_LISTS)
    fun getMoviesLists(@RequestBody info: MoviesModel.ListModel):ResponseEntity<*>{
        val lists = moviesServices.getLists(info.userid)

        return ResponseEntity
            .status(200)
            .body(lists)
    }

    @GetMapping(Movies.GET_LIST)
    fun getMoviesList(@PathVariable id: Int, @RequestBody info: MoviesModel.ListModel): ResponseEntity<*>{
        val list = moviesServices.getList(id, info.userid)

        return ResponseEntity
            .status(200)
            .body(list)
    }

    @PostMapping(Movies.CREATE_LIST)
    fun createMoviesList(@RequestBody info: MoviesModel.ListModel): ResponseEntity<*>{
        val list = moviesServices.createList(info.userid)

        return ResponseEntity
            .status(200)
            .body(list)
    }

    @PostMapping(Movies.DELETE_LIST)
    fun deleteMoviesList(@PathVariable id: Int, @RequestBody info: MoviesModel.ListModel): ResponseEntity<*>{
        val list = moviesServices.deleteList(id, info.userid)

        return ResponseEntity
            .status(200)
            .body(list)
    }

    @DeleteMapping(Movies.DELETE_MOVIE_FROM_LIST)
    fun deleteSerieFromList(@PathVariable id: Int, @PathVariable mid: String?, @RequestBody info: MoviesModel.ListModel): ResponseEntity<*>{
        val list = moviesServices.deleteMovieFromList(id, movieId = mid, info.userid)

        return ResponseEntity
            .status(200)
            .body(list)
    }
}