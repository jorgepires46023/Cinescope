package pt.isel.ps.cinescope.domain

data class Search(val page: Int?, val results: Array<Result>?, val total_results: Int?, val total_pages: Int?)

data class Result(val poster_path: String?, val id: Int?, val title: String?, val name: String?, val media_type: String?, val popularity: Int?)

