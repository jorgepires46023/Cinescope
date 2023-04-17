package pt.isel.ps.cinescope.controllers.models

class SeriesModel {
    data class AddInputModel(val listid: Int?, val userid: Int?)
    data class ChangeStateModel(val state: String?, val userid: Int?)
    data class ListModel(val userid: Int?, val name: String?)
    data class EpisodeModel(val userid: Int?)
}