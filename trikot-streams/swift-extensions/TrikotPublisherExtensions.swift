import Foundation
import TRIKOT_FRAMEWORK_NAME

extension NSObject {
    private final class iOSCancellableManagerHolder {
        var cancellableManager = CancellableManager()

        deinit {
            cancellableManager.cancel()
        }
    }

    private struct AssociatedKeys {
        static var cancellableKey = UnsafeMutablePointer<Int8>.allocate(capacity: 1)
    }

    public func unsubscribeFromAllPublisher() {
        objc_setAssociatedObject(self, AssociatedKeys.cancellableKey, nil, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN_NONATOMIC)
    }

    public var trikotInternalPublisherCancellableManager: CancellableManager {
        if let iosCancellableManagerHolder = objc_getAssociatedObject(self, AssociatedKeys.cancellableKey) as? iOSCancellableManagerHolder {
            return iosCancellableManagerHolder.cancellableManager
        } else {
            let iosCancellableManagerHolder = iOSCancellableManagerHolder()
            objc_setAssociatedObject(self, AssociatedKeys.cancellableKey, iosCancellableManagerHolder, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN_NONATOMIC)
            return iosCancellableManagerHolder.cancellableManager
        }
    }

    public func observe<V>(_ publisher: Publisher, toClosure closure: @escaping ((V) -> Void)) {
        if let publisher = publisher as? NeverPublisher<AnyObject> {
        }
        else if let publisher = publisher as? JustPublisher<AnyObject> {
            (publisher.values as! NSArray).forEach { closure($0 as! V) }
        } else {
            observe(cancellableManager: trikotInternalPublisherCancellableManager, publisher: publisher, toClosure: closure)
        }
    }

    public func observe<V>(cancellableManager: CancellableManager, publisher: Publisher, toClosure closure: @escaping ((V) -> Void)) {
        if let publisher = publisher as? NeverPublisher<AnyObject> {
        }
        else if let publisher = publisher as? JustPublisher<AnyObject> {
            (publisher.values as! NSArray).forEach { closure($0 as! V) }
        } else {
            PublisherExtensionsKt.subscribe(publisher, cancellableManager: cancellableManager) {(value: Any?) in
                if Thread.current.isMainThread {
                    assert(value is V, "Incorrect binding value type - Cannot cast \(value.self) to \(V.self)")
                    closure(value as! V)
                } else {
                    MrFreezeKt.freeze(objectToFreeze: value)
                    DispatchQueue.main.async {
                        assert(value is V, "Incorrect binding value type - Cannot cast \(value.self) to \(V.self)")
                        closure(value as! V)
                    }
                }
            }
        }
    }

    public func bind<T, V>(_ publisher: Publisher, _ keyPath: ReferenceWritableKeyPath<T, V>) {
        observe(publisher) {[weak self] (newValue: V) in
            guard let strongSelf = self as? T else { return }
            strongSelf[keyPath: keyPath] = newValue
        }
    }

    public func observe<V>(_ concretePublisher: ConcretePublisher<V>, toClosure closure: @escaping ((V) -> Void)) {
        observe(cancellableManager: trikotInternalPublisherCancellableManager, concretePublisher: concretePublisher, toClosure: closure)
    }

    public func observe<V>(cancellableManager: CancellableManager, concretePublisher: ConcretePublisher<V>, toClosure closure: @escaping ((V) -> Void)) {
        PublisherExtensionsKt.subscribe(concretePublisher, cancellableManager: cancellableManager) {(value: Any?) in
            if Thread.current.isMainThread {
                closure(value as! V)
            } else {
                MrFreezeKt.freeze(objectToFreeze: value)
                DispatchQueue.main.async {
                    closure(value as! V)
                }
            }
        }
    }

    public func bind<T, V>(_ concretePublisher: ConcretePublisher<V>, _ keyPath: ReferenceWritableKeyPath<T, V>) {
        observe(concretePublisher) {[weak self] (newValue: V) in
            guard let strongSelf = self as? T else { return }
            strongSelf[keyPath: keyPath] = newValue
        }
    }
}

extension Publisher {
    public func first() -> Publisher {
        return PublisherExtensionsKt.first(self)
    }
}
