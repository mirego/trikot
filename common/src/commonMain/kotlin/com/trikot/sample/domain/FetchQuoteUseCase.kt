package com.trikot.sample.domain

import org.reactivestreams.Publisher

interface FetchQuoteUseCase {
    fun fetchQuote(): Publisher<String>
}
