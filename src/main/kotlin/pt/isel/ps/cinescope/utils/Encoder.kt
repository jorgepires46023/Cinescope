package pt.isel.ps.cinescope.utils

interface Encoder {

    fun encodeInfo(info: String): String

    fun validateInfo(info: String, encodedInfo: String): Boolean

}