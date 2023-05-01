package pt.isel.ps.cinescope.controllers.models

class MoviesModel {
    data class AddInputModel(val tmdbId: Int?, val listid: Int?, val userid: Int?)
    data class ChangeStateModel(val state: String?, val userid: Int?)
    data class ListModel(val userid: Int?, val name: String?)
}