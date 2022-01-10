package com.trikot.sample.domain.impl

import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.streams.reactive.onErrorReturn
import com.mirego.trikot.streams.reactive.shared
import com.trikot.sample.domain.FetchQuoteUseCase
import com.trikot.sample.repositories.QuoteRepository
import org.reactivestreams.Publisher

class FetchQuoteUseCaseImpl(private val quoteRepo: QuoteRepository) :
    FetchQuoteUseCase {
    override fun fetchQuote(): Publisher<String> {
        return quoteRepo.getQuotes()
            .map { it.firstOrNull()?.quote ?: "No quotes" }
            .onErrorReturn { it.message ?: "Unknown error occurred" }
            .shared()
    }
}
