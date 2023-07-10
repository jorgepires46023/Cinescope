package com.example.cinescope.utils

import com.example.cinescope.services.exceptions.IllegalPathInitializationException
import com.example.cinescope.services.exceptions.IllegalPathVariablesException
import com.example.cinescope.services.exceptions.MismatchingPathVariablesException
import java.net.URL

//TODO implement exceptions in cases that '/' path doesn't contain at the beginning

/** Adds specific path to given URL **/
fun URL.joinPath(path: String): URL {
    if (path.first() != '/') throw IllegalPathInitializationException()
    return URL(this.toString() + path)
}

//EXAMPLE FULL URL "http://cinescope.com/series/{id}/list/{lid}"

/** Adds variables inside List to path and then joins it to given URL **/
fun URL.joinPathWithVariables(path: String, variables: List<String>): URL{
    val pathElements = path.split('/') as MutableList<String>
    pathElements.removeFirst()  //removes first index which is empty (because of our path model always must be initialized with '/')
    if(!validatePathVariables(pathElements, variables)) throw IllegalPathVariablesException()
    var finalPath = ""
    var variableIdx = 0
    pathElements.forEach {
        finalPath += if(it.first() != '{') "/$it"
        else "/${variables[variableIdx++]}"
    }
    return this.joinPath(finalPath)
}
/** Validates if the elements inside the list contain equal strings and match pathVariables list provided **/
fun validatePathVariables(list: List<String>, variablesList: List<String>):Boolean{
    val predicate: (String) -> Boolean = {it.first()=='{'}
    list.count(predicate)
    if(list.count(predicate) != variablesList.size) throw MismatchingPathVariablesException()
    return list.size == list.distinct().count()
}