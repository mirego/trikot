package org.reactivestreams

interface Subscription {
    /**
     * No events will be sent by a [Publisher] until demand is signaled via this method.
     *
     *
     * It can be called however often and whenever needed—but if the outstanding cumulative demand ever becomes Long.MAX_VALUE or more,
     * it may be treated by the [Publisher] as "effectively unbounded".
     *
     *
     * Whatever has been requested can be sent by the [Publisher] so only signal demand for what can be safely handled.
     *
     *
     * A [Publisher] can send less than is requested if the stream ends but
     * then must emit either [Subscriber.onError] or [Subscriber.onComplete].
     *
     * @param n the strictly positive number of elements to requests to the upstream [Publisher]
     */
    fun request(n: Long)

    /**
     * Request the [Publisher] to stop sending data and clean up resources.
     *
     *
     * Data may still be sent to meet previously signalled demand after calling cancel.
     */
    fun cancel()
}
