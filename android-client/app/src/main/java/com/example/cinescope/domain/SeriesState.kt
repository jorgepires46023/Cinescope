package com.example.cinescope.domain

enum class SeriesState(val state: String) {
    PTW("PTW"), WATCHED("Watched"), WATCHING("Watching"), NO_STATE("No State");
    companion object {
        fun getStates(): List<String>{
            return SeriesState.values().map{ s -> s.state}
        }
    }
}