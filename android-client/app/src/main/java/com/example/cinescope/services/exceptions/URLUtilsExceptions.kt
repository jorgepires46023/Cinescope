package com.example.cinescope.services.exceptions

abstract class PathException(override val message: String):Exception()
class IllegalPathInitializationException: PathException("Wrong initialization joining path to URL")

class IllegalPathVariablesException:
    PathException("Cannot create path with two or more path variables with the same name")

class MismatchingPathVariablesException: PathException("Mismatching number of path variables found")