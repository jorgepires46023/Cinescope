package com.example.cinescope.utils

import java.net.URL

fun URL.joinPath(path: String): URL = URL(this.toString() + path)
