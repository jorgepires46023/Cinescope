

export type MovieDetails = {
    id: number,
    imdb_id: string,
    original_title: string,
    overview: string,
    poster_path: string,
    release_date: string,
    runtime: number,
    status: string,
    title: string,
    backdrop_path: string
}

export type ExternalIds = {
    imdb_id: string,
    facebook_id: string,
    twitter_id: string
}

export type WatchProviders = {
    id: number,
    results: {
        PT: WatchProvidersDetails
    }
}

export type WatchProvidersDetails = {
    link: string,
    flatrate: [ProviderInfo],
    rent: [ProviderInfo],
    buy: [ProviderInfo]
}

export type ProviderInfo = {
    display_priority: number,
    provider_name: string
    logo_path: string
}

export type MovieDetailsResults = {
    movieDetails: MovieDetails,
    watchProviders: WatchProviders,
    externalIds: ExternalIds | {}
}

export type User = {
    id: number,
    name: string,
    email: string,
    password: string,
    token: string,
    state: string
}

export type Results = {
    poster_path: string,
    id: number,
    title: string,
    name: string,
    media_type: string,
    popularity: number
}

export type SearchResults = {
    page: number,
    results: [Results],
    total_results: number,
    total_pages: number
}

export type ListElem = {
    poster_path: string,
    id: number,
    title: string,
    name: string | null,
    media_type: string,
    popularity: number
}

export type List = {
    page: number,
    results: [ListElem],
    total_results: number,
    total_pages: number
}

export type SerieDetailsResults = {
    serieDetails: SerieDetails,
    watchProviders: WatchProviders,
    externalIds: ExternalIds
}

export type SerieDetails = {
    id: number,
    name: string,
    seasons: [Seasons],
    status: string,
    poster_path: string,
    overview: string,
    backdrop_path: string
}

export type SeasonDetailsResults = {
    seasonDetails: SeasonDetails,
    watchProviders: WatchProviders
}

export type Seasons = {
    episode_count: number,
    id: number,
    name: string,
    season_number: number
}

export type LoginInfo = {
    email: string,
    password: string,
}

export type NewUserInfo = {
    username: string,
    password: string,
    email: string
}

export type Episode = {
    air_date: string,
    episode_number: number,
    id: number,
    name: string,
    overview: string,
    still_path: string,
}

export type SeasonDetails = {
    air_date: string ,
    episodes: [Episode],
    season_number: number
}

export type UserListsElems = {
    id: number,
    name: string
}

export type MovieUserData = {
    id: number,
    state: string,
    lists: [UserListsElems]
}
    
/*                        Empty Objects                          */ 

export const EMPTY_EXTERNAL_IDS: ExternalIds = {
    imdb_id: "",
    facebook_id: "",
    twitter_id: ""
}

export const EMPTY_PROVIDERS_INFO: ProviderInfo = {
    display_priority: 0,
    provider_name: "",
    logo_path: ""
}

export const EMPTY_WATCH_PROVIDERS_DETAILS: WatchProvidersDetails = {
    link: "",
    flatrate: [EMPTY_PROVIDERS_INFO],
    rent: [EMPTY_PROVIDERS_INFO],
    buy: [EMPTY_PROVIDERS_INFO]
}

export const EMPTY_WATCH_PROVIDERS: WatchProviders = {
    id: 0,
    results: {
        PT: EMPTY_WATCH_PROVIDERS_DETAILS
    }
}

export const EMPTY_MOVIE_DETAILS: MovieDetails = {
    id: 0,
    imdb_id: "",
    original_title: "",
    overview: "",
    poster_path: "",
    release_date: "",
    runtime: 0,
    status: "",
    title: "",
    backdrop_path: ""
}

export const EMPTY_MOVIE_DETAILS_RESULTS: MovieDetailsResults = {
    movieDetails: EMPTY_MOVIE_DETAILS,
    watchProviders: EMPTY_WATCH_PROVIDERS,
    externalIds: EMPTY_EXTERNAL_IDS
}

export const EMPTY_SEASONS: Seasons = {
    episode_count: 0,
    id: 0,
    name: "",
    season_number: 0
}

export const EMPTY_SERIE_DETAILS: SerieDetails = {
    id: 0,
    name: "",
    seasons: [EMPTY_SEASONS],
    status: "",
    poster_path: "",
    overview: "",
    backdrop_path: ""
}

export const EMPTY_SERIE_DETAILS_RESULTS: SerieDetailsResults = {
    serieDetails: EMPTY_SERIE_DETAILS,
    watchProviders: EMPTY_WATCH_PROVIDERS,
    externalIds: EMPTY_EXTERNAL_IDS
}

export const EMPTY_USER: User = {
    id: 0,
    name: "",
    email: "",
    password: "",
    token: "",
    state: ""
}

export const EMPTY_LOGIN_INFO: LoginInfo = {
    email: "",
    password: "",
}

export const EMPTY_RESULTS: Results = {
    poster_path: "",
    id: 0,
    title: "",
    name: "",
    media_type: "",
    popularity: 0
}

export const EMPTY_SEARCH_RESULTS: SearchResults = {
    page: 0,
    results: [EMPTY_RESULTS],
    total_results: 0,
    total_pages: 0
}

export const EMPTY_NEW_USER_INFO: NewUserInfo = {
    username: "",
    password: "",
    email: "",
}

export const EMPTY_LIST_ELEM: ListElem = {
    poster_path: "",
    id: 0,
    title: "",
    name: "",
    media_type: "",
    popularity: 0
}

export const EMPTY_LIST: List = {
    page: 0,
    results: [EMPTY_LIST_ELEM],
    total_results: 0,
    total_pages: 0
}


export const EMPTY_EPISODE: Episode = {
    air_date: "",
    episode_number: 0,
    id: 0,
    name: "",
    overview: "",
    still_path: ""
}

export const EMPTY_SEASON_DETAILS: SeasonDetails = {
    air_date: "",
    episodes: [EMPTY_EPISODE],
    season_number: 0
}

export const EMPTY_SEASON_DETAILS_RESULTS: SeasonDetailsResults = {
    seasonDetails: EMPTY_SEASON_DETAILS,
    watchProviders: EMPTY_WATCH_PROVIDERS
}

export const EMPTY_USER_LISTS_ELEMS: UserListsElems = {
    id: 0,
    name: ""
}

export const EMPTY_MOVIE_USER_DATA: MovieUserData = {
    id: 0,
    state: "",
    lists: [EMPTY_USER_LISTS_ELEMS]
}