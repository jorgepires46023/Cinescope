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