import Combine
import TRIKOT_FRAMEWORK_NAME

public class PublisherAdapter<T>: Combine.Publisher {
    private let publisher: TRIKOT_FRAMEWORK_NAME.Publisher

    public typealias Output = T

    public typealias Failure = Never

    public func receive<S>(subscriber: S) where S: Combine.Subscriber, Failure == S.Failure, Output == S.Input {
        publisher.subscribe(s: SubscriberAdapter(subscriber))
    }

    public init(_ publisher: TRIKOT_FRAMEWORK_NAME.Publisher) {
        self.publisher = publisher
    }
}

public class SubscriberAdapter<S: Combine.Subscriber>: TRIKOT_FRAMEWORK_NAME.Subscriber {
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
        guard let t = t as? S.Input else { 
            assert(false, "Invalid cast")
            return
        }
        if Thread.current.isMainThread {
            _ = subscriber.receive(t)
        } else {
            DispatchQueue.main.async {
                _ = self.subscriber.receive(t)
            }
        }
    }

    public func onSubscribe(s: TRIKOT_FRAMEWORK_NAME.Subscription) {
        subscriber.receive(subscription: SubscriptionAdapter(s))
    }
}

public class SubscriptionAdapter: Combine.Subscription {
    private let subscription: TRIKOT_FRAMEWORK_NAME.Subscription

    init(_ subscription: TRIKOT_FRAMEWORK_NAME.Subscription) {
        self.subscription = subscription
    }

    public func request(_ demand: Subscribers.Demand) {
        if let max = demand.max {
            subscription.request(n: Int64(max))
        } else {
            subscription.request(n: Int64.max)
        }
    }

    public func cancel() {
        subscription.cancel()
    }
}

extension TRIKOT_FRAMEWORK_NAME.Publisher {
    public func asCombinePublisher<T>(type: T.Type) -> AnyPublisher<T, Never> {
        AnyPublisher(PublisherAdapter<T>(self))
    }
}
