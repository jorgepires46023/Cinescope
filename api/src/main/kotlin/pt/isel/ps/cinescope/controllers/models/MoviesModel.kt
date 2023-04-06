package pt.isel.ps.cinescope.controllers.models

class MoviesModel {
    data class AddInputModel(val listid: Int?)
    data class ChangeStateModel(val state: String?)
    data class ListModel(val userid: Int?)
}