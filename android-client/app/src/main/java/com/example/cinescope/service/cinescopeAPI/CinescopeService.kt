package com.example.cinescope.service.cinescopeAPI

import com.example.cinescope.service.exceptions.UnexpectedResponseException
import com.example.cinescope.service.exceptions.UnsuccessfulResponseException
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.internal.closeQuietly
import java.lang.reflect.Type
import java.net.URL

abstract class CinescopeService(_cinescopeURL: URL){
    internal var cinescopeURL: URL
    internal val httpClient: OkHttpClient = OkHttpClient()
    internal val gson: Gson = Gson()

    init {
        this.cinescopeURL=_cinescopeURL
    }

    private val JsonMediaType = ("application/json").toMediaType()

    enum class Method(val method: String) {
        GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE")
    }

    /** Builds a request **/
    internal fun buildRequest(
        url: URL, method: Method = Method.GET,
        body: RequestBody? = null, token: String? = null
    ) =
        Request.Builder()
            .url(url)
            .method(method.method, body)
            .let {
                if (token == null) it
                else it.header("Authorization", "Bearer $token")
            }
            .build()

    /** It handles response content **/
    internal fun <T> handleResponse(response: Response, type: Type): T {
        val body = response.body
        val contentType = body?.contentType()

        return try {
            if (response.isSuccessful && contentType == JsonMediaType) {
                gson.fromJson<T>(body.string(), type)
            }else {
                throw UnexpectedResponseException(response)
            }
        } catch (e: JsonSyntaxException) {
            throw UnsuccessfulResponseException(e.message)
        } finally {
            response.body?.closeQuietly()
        }
    }
}