import Combine
import TRIKOT_FRAMEWORK_NAME

public extension TrikotViewmodelsDeclarativeSample.Publisher {
    func eraseToAnyPublisher<T>() -> AnyPublisher<T, Never> where Self: ConcretePublisher<T>, T: AnyObject {
        ConcreatePublisherAdapter<T>(self).eraseToAnyPublisher()
    }
}

public class ConcreatePublisherAdapter<T: AnyObject>: Combine.Publisher {
    private let publisher: ConcretePublisher<T>

    public typealias Output = T

    public typealias Failure = Never

    public func receive<S>(subscriber: S) where S: Combine.Subscriber, Failure == S.Failure, Output == S.Input {
        publisher.subscribe(s: SubscriberAdapter(subscriber))
    }

    public init(_ publisher: ConcretePublisher<T>) {
        self.publisher = publisher
    }
}

public class SubscriberAdapter<S: Combine.Subscriber>: TrikotViewmodelsDeclarativeSample.Subscriber {
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
            Foundation.DispatchQueue.main.sync {
                _ = self.subscriber.receive(t)
            }
        }
    }

    public func onSubscribe(s: TrikotViewmodelsDeclarativeSample.Subscription) {
        subscriber.receive(subscription: SubscriptionAdapter(s))
    }
}

public class SubscriptionAdapter: Combine.Subscription {
    private let subscription: TrikotViewmodelsDeclarativeSample.Subscription

    init(_ subscription: TrikotViewmodelsDeclarativeSample.Subscription) {
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

typealias CancellableBag = Set<AnyCancellable>

extension CancellableBag {
    mutating func cancelAll() {
        forEach { $0.cancel() }
        removeAll()
    }
}
