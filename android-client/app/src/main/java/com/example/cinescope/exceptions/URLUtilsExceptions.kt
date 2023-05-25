package com.example.cinescope.exceptions

class IllegalPathInitializationException(override val message: String = "Wrong initialization joining path to URL"): Exception()

class IllegalPathVariablesException(override val message: String = "Cannot create path with two or more path variables with the same name"): Exception()

class MismatchingPathVariablesException(override val message: String = "Mismatching number of path variables found"): Exception()