package com.trikot.sample.repositories

import com.trikot.sample.models.Quote
import org.reactivestreams.Publisher

interface QuoteRepository {
    fun getQuotes(): Publisher<List<Quote>>
}
