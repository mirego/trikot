package com.mirego.trikot.http

val HttpResponse.bodyString: String?
    get() = bodyByteArray?.decodeToString()
