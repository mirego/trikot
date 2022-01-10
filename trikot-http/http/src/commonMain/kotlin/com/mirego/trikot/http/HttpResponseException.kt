package com.mirego.trikot.http

class HttpResponseException(
    val httpResponse: HttpResponse
) : Exception("HTTP error ${httpResponse.statusCode}")
