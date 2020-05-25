import Combine
import TRIKOT_FRAMEWORK_NAME

public class PublisherAdapter<T>: Combine.Publisher {
    private let publisher: ShopCastFramework.Publisher

    public typealias Output = T

    public typealias Failure = Never

    public func receive<S>(subscriber: S) where S: Combine.Subscriber, Failure == S.Failure, Output == S.Input {
        publisher.subscribe(s: SubscriberAdapter(subscriber))
    }

    public init(_ publisher: ShopCastFramework.Publisher) {
        self.publisher = publisher
    }
}

public class SubscriberAdapter<S: Combine.Subscriber>: ShopCastFramework.Subscriber {
    private let subscriber: S

    public init(_ subscriber: S) {
        self.subscriber = subscriber
    }

    public func onComplete() {
        subscriber.receive(completion: .finished)
    }

    public func onError(t: KotlinThrowable) {
        assert(false, "Error should be catch by KMP code. Error was: \(t)")
    }

    public func onNext(t: Any?) {
        guard let t = t as? S.Input else { assert(false, "Invalid cast") }
        _ = subscriber.receive(t)
    }

    public func onSubscribe(s: ShopCastFramework.Subscription) {
        subscriber.receive(subscription: SubscriptionAdapter(s))
    }
}

public class SubscriptionAdapter: Combine.Subscription {
    private let subscription: ShopCastFramework.Subscription

    init(_ subscription: ShopCastFramework.Subscription) {
        self.subscription = subscription
    }

    public func request(_ demand: Subscribers.Demand) {
        if let max = demand.max {
            subscription.request(n: Int64(max))
        }
    }

    public func cancel() {
        subscription.cancel()
    }
}

extension ShopCastFramework.Publisher {
    public func asCombinePublisher<T>(type: T.Type) -> PublisherAdapter<T> {
        PublisherAdapter<T>(self)
    }
}
