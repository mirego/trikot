package com.trikot.sample.domain.impl

import com.mirego.trikot.streams.reactive.*
import com.trikot.sample.domain.FetchQuoteUseCase
import com.trikot.sample.repositories.QuoteRepository
import org.reactivestreams.Publisher

class FetchQuoteUseCaseImpl(private val quoteRepo: QuoteRepository) :
    FetchQuoteUseCase {
    override fun fetchQuote(): Publisher<String> {
        return Publishers.behaviorSubject()
    }
}
