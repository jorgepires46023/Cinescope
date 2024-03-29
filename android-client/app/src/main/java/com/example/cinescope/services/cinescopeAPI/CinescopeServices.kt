package com.example.cinescope.services.cinescopeAPI

import com.example.cinescope.services.MethodHTTP
import com.example.cinescope.services.exceptions.CookieNotFoundResponseException
import com.example.cinescope.services.exceptions.CookieParsingException
import com.example.cinescope.services.exceptions.UnexpectedResponseException
import com.example.cinescope.services.exceptions.UnsuccessfulResponseException
import com.example.cinescope.utils.cookieParser
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.Cookie
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.internal.closeQuietly
import java.lang.reflect.Type
import java.net.URL

abstract class CinescopeServices(
    private val _gson: Gson,
    private val _httpClient: OkHttpClient) {

    private val gson : Gson = this._gson
    val httpClient: OkHttpClient = this._httpClient

    private val JsonMediaType = ("application/json").toMediaType()

    /** Builds a request **/
    internal fun buildRequest(
        url: URL, method: MethodHTTP = MethodHTTP.GET,
        body: RequestBody? = null, cookie: Cookie? = null
    ) =
        Request.Builder()
            .url(url)
            .header("Content-Type", JsonMediaType.toString())
            .method(method.type, body)
            .let {
                if(cookie == null) it
                else it.header("Cookie", cookie.toString())
            }
            .build()

    internal fun handleCookie(response: Response): Cookie {
        val cookie = response.headers["Set-Cookie"] ?: throw CookieNotFoundResponseException("Cookie not found")
        return cookieParser(cookie) ?: throw CookieParsingException("Error parsing cookie")
    }
    /** It handles response content **/
    internal fun <T> handleResponse(response: Response, type: Type): T {
        val body = response.body
        val contentType = body?.contentType()

        return try {
            if (response.isSuccessful && contentType == JsonMediaType) {
                gson.fromJson<T>(body.string(), type)
            } else {
                throw UnsuccessfulResponseException(response)
            }
        } catch (e: JsonSyntaxException) {
            throw UnexpectedResponseException(e.message)
        } finally {
            response.body?.closeQuietly()
        }
    }

    internal fun Any.toJsonBody(): RequestBody =
        gson.toJson(this).toRequestBody(JsonMediaType)

    internal fun handleEmptyResponse(response: Response){
        try {
            if(!response.isSuccessful) throw UnsuccessfulResponseException(response)
        }finally {
            response.body?.closeQuietly()
        }

    }
}