package pt.isel.ps.cinescope.services

import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.domain.Search
import pt.isel.ps.cinescope.utils.TmdbService


@Component
class SearchServices(val tmdbServices: TmdbService) {

    fun searchByQuery(input: String?): Search? {
        if (input.isNullOrBlank()) return null
        return tmdbServices.fetchQuery(input)
    }

}