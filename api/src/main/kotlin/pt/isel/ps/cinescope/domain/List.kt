package pt.isel.ps.cinescope.domain

data class ListInfo(val id: Int, val name: String)

data class ListDetails(val info: ListInfo, val results: List<Any>)