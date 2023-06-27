package com.example.cinescope.domain

enum class MovieState(val state: String) {
    PTW("PTW"), WATCHED("Watched"), NO_STATE("No State");

    companion object {
        fun getStates(): List<String>{
            return values().map{s -> s.state}
        }
    }
}
