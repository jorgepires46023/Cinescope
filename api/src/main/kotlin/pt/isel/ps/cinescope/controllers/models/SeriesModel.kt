package pt.isel.ps.cinescope.controllers.models

class SeriesModel {
    data class AddInputModel(val tmdbId: Int?)
    data class StateModel(val state: String?)
    data class ListModel(val name: String?)
    data class EpisodeModel(val seasonNumber: Int?, val episodeNumber: Int?)
}