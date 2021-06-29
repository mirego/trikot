import Foundation
import TRIKOT_FRAMEWORK_NAME

extension ViewModel {
    public func publisher<T, V>(for property: KeyPath<T, V>) -> PublisherAdapter<V> {
        PublisherAdapter(publisher: publisherForPropertyName(propertyName: NSExpression(forKeyPath: property).keyPath))
    }
}

public class PublisherAdapter<T>: Publisher {
    private let publisher: Publisher

    public init(publisher: Publisher) {
        self.publisher = publisher
    }

    public func subscribe(s: Subscriber) {
        publisher.subscribe(s: s)
    }
}
