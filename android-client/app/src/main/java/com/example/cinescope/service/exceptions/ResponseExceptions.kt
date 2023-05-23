package com.example.cinescope.service.exceptions

import okhttp3.Response

abstract class ApiException(override val message: String) : Exception()

class UnexpectedResponseException(response: Response? = null) : ApiException("Unexpected ${response?.code} response from the API")

class UnsuccessfulResponseException(error : String?) : ApiException("Unsuccessful response from the API: $error")