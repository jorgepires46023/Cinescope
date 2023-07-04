package com.example.cinescope.utils

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Extension function used to send [this] request using [okHttpClient] and process the
 * received response with the given [handler]. Note that [handler] is called from a
 * OkHttp IO Thread.
 *
 * @receiver the request to be sent
 * @param okHttpClient  the client from where the request is sent
 * @param handler       the handler function, which is called from a IO thread.
 *                      Be mindful of threading issues.
 * @return the result of the response [handler]
 * @throws  [IOException] if a communication error occurs.
 * @throws  [Throwable] if any error is thrown by the response handler.
 */
suspend fun <T> OkHttpClient.send(request: Request, handler: (Response) -> T): T =

    suspendCoroutine { continuation ->
        this.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                continuation.resumeWithException(e)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    continuation.resume(handler(response))
                }
                catch (t: Throwable) {
                    continuation.resumeWithException(t)
                }
            }
        })
    }

//TODO: ver https://github.com/isel-leic-pdm/2223I-LEIC52D-LEIRT51D/blob/main/QuoteOfDay/app/src/main/java/palbp/laboratory/demos/quoteofday/utils/okhttpx.kt