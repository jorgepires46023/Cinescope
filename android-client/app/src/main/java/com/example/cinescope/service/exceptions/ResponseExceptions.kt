package com.example.cinescope.service.exceptions

import okhttp3.Response

abstract class ApiException(override val message: String) : Exception()

class UnexpectedResponseException(error : String?) : ApiException("Unexpected response from the API: $error")

class UnsuccessfulResponseException(response: Response? = null) : ApiException("Unsuccessful response with status code: ${response?.code}")