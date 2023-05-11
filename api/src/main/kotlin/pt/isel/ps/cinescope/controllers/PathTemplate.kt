package pt.isel.ps.cinescope.controllers

object Users {
    const val CREATE_USER = "/users"                                            // Register a new user.
    const val DELETE_USER = "/users/{id}"                                       // Deactivate user
    const val UPDATE_USER = "/users/{id}/edit"                                  // Update user
    const val GET_USER_INFO = "users/{id}"                                      // Gets user info
    const val LOGIN = "/login"                                                  // Logins the user
}

object Movies{
    const val ADD_MOVIE = "/movies/{id}/list"                                   // Add movie to watchlist/otherlists
    const val CHANGE_STATE = "/movies/{id}/state"                               // Change state of movie(PTW/WATCHED)
    const val GET_LIST_BY_STATE = "/movies/state/{state}"                       // Gets all movies with state {state}
    const val GET_MOVIES_LISTS = "/movies/lists"                                // Gets all lists from user
    const val GET_LIST = "/movies/list"                                         // Gets movies list
    const val CREATE_LIST = "/movies/list"                                      // Creates new movie list
    const val DELETE_LIST = "/movies/list/{id}"                                 // Deletes list
    const val DELETE_MOVIE_FROM_LIST = "list/{id}/movie/{mid}"                  // Deletes movie from specific Movie List
}

object Series{
    const val ADD_SERIE = "/series/{id}/list"                                   // Add serie to watchlist/otherlists
    const val CHANGE_STATE = "/series/{id}/state"                               // Change state of serie(PTW/WATCHING/WATCHED)
    const val GET_PTW_LIST = "/series/ptw"                                      // Gets all series with state PTW
    const val GET_WATCHING_LIST = "/series/watching"                            // Gets all series with state PTW
    const val GET_WATCHED_LIST = "/series/watched"                              // Gets all series with state WATCHED
    const val ADD_WATCHED_EP = "series/{id}/ep/{epid}"                          // Add episode to list of watched episodes
    //const val REMOVE_WATCHED_EP = "series/{id}/ep/{epid}"                     // Remove episode from list of watched episodes
    const val GET_WATCHED_EP_LIST = "series/{id}"                               // Get watched episodes list from series
    const val GET_SERIES_LISTS = "/series/lists"                                // Gets all lists from user
    const val GET_LIST = "/series/list/{id}"                                    // Gets series list
    const val CREATE_LIST = "/series/list"                                      // Creates new movie list
    const val DELETE_LIST = "/series/list/{id}"                                 // Deletes list
    const val DELETE_SERIE_FROM_LIST ="/list/{id}/serie/{sid}"                  // Deletes serie from specific Serie List
}

object Searches{
    const val MOVIE_DETAILS = "/api_movies/{id}"                                    // Search Movie details on external API
    const val SEARCH_QUERIE = "/search/{query}"                                     // Search input on external API
    const val SERIE_DETAILS = "/api_series/{id}"                                    // Search Serie details on external API
    const val SEASON_DETAILS = "/api_series/{id}/season/{seasonnum}"                // Search season details on external API
    const val EPISODE_DETAILS = "/api_series/{id}/season/{seasonnum}/ep/{epnum}"    // Search episode details on external API
    const val MOVIE_RECOMMENDATIONS = "/api_movies/{id}/recommendations"            // Returns movie recommendations based on id provided
    const val SERIE_RECOMMENDATIONS = "/api_series/{id}/recommendations"            // Returns serie recommendations based on id provided
    const val GET_POPULAR_MOVIES = "/api_movies/popular"                            // Returns popular movies
    const val GET_POPULAR_SERIES = "/api_series/popular"                            // Returns popular movies
}
