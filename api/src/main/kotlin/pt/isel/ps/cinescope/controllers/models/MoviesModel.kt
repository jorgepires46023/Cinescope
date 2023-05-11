package pt.isel.ps.cinescope.controllers.models

class MoviesModel {
    data class AddInputModel(val tmdbId: Int?, val listid: Int?)
    data class ChangeStateModel(val state: String?)
    data class ListModel(val name: String?)
}