package com.example.cinescope.services.exceptions

abstract class MappingException(override val message: String) : Exception()

class UnexpectedMappingException : MappingException("Unexpected error mapping data into object")