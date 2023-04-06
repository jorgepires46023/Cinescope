package pt.isel.ps.cinescope.controllers.models

class SeriesModel {
    data class AddInputModel(val listid: Int?, val userid: Int?)
    data class ChangeStateModel(val state: String?, val userid: Int?)
    data class ListModel(val userid: Int?)
    data class EpisodeModel(val userid: Int?)
}