package pt.isel.ps.cinescope.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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
}