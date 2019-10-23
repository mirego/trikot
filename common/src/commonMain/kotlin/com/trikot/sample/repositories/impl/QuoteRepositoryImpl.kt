package com.trikot.sample.repositories.impl

import com.mirego.trikot.streams.reactive.Publishers
import com.trikot.sample.models.Quote
import com.trikot.sample.repositories.QuoteRepository
import org.reactivestreams.Publisher

class QuoteRepositoryImpl(): QuoteRepository {
    override fun getQuotes(): Publisher<List<Quote>> {
        return Publishers.behaviorSubject()
    }
}
