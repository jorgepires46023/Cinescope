package pt.isel.ps.cinescope.utils

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import pt.isel.ps.cinescope.domain.Search

const val TMDB_API_KEY = "api_key=1f54ddc9f7ff5ecab4807e013dddc65b"
const val TMDB_URL = "https://api.themoviedb.org/3/"

@Service
class TmdbService {

    private fun fetch(path: String): WebClient.ResponseSpec{
        val client = WebClient.create(TMDB_URL)
        return client.get().uri(path).retrieve()
    }

    fun fetchQuery(input: String) =
        fetch("search/multi?$TMDB_API_KEY&page=1&query=$input").bodyToMono<Search>().block()


}