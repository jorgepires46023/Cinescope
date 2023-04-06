package pt.isel.ps.cinescope.controllers

object Users {
    const val CREATE_USER = "/users"                    // Register a new user.
    const val DELETE_USER = "/users/{id}"               // Deactivate user
    const val UPDATE_USER = "/users/{id}"               // Update user
    const val GET_USER_INFO = "users/{id}"              // Gets user info
    const val GET_USER_LISTS = "users/{id}/lists"       // Gets all user lists
    const val LOGIN = "/login"                          // Logins the user
}

object Movies{
    const val ADD_MOVIE = "/movies/{id}/list"           // Add movie to watchlist/otherlists
    const val CHANGE_STATE = "/movies/{id}/state"       // Change state of movie(PTW/WATCHED)
    const val GET_MOVIES_LISTS = "/movies/lists"        // Gets all lists from user
    const val GET_LIST = "/movies/list/{id}"            // Gets movies list
    const val CREATE_LIST = "/movies/list"              // Creates new movie list
    const val DELETE_LIST = "/movies/list/{id}"         // Deletes list
}

object Series{
    const val ADD_SERIE = "/series/{id}/list"            // Add serie to watchlist/otherlists
    const val CHANGE_STATE = "/series/{id}/state"        // Change state of serie(PTW/WATCHING/WATCHED)
    const val ADD_WATCHED_EP = "series/{id}/ep/{eid}"    // Add episode to list of watched episodes
    const val REMOVE_WATCHED_EP = "series/{id}/ep/{eid}" // Remove episode from list of watched episodes
    const val GET_WATCHED_EP_LIST = "series/{id}"        // Get watched episodes list from series
    const val GET_SERIES_LISTS = "/series/lists"         // Gets all lists from user
    const val GET_LIST = "/series/list/{id}"             // Gets series list
    const val CREATE_LIST = "/series/list"               // Creates new movie list
    const val DELETE_LIST = "/series/list/{id}"          // Deletes list
}
