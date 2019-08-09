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
        observe(cancellableManager: trikotInternalPublisherCancellableManager, publisher: publisher, toClosure: closure)
    }

    public func observe<V>(cancellableManager: CancellableManager, publisher: Publisher, toClosure closure: @escaping ((V) -> Void)) {
        PublisherExtensionsKt.subscribe(publisher, cancellableManager: cancellableManager) { (value: Any?) in
            MrFreezeKt.freeze(objectToFreeze: value)
            guard let typedValue = value as? V else {
                print("Incorrect binding value type" + (value is NSNull ? " received NSNull, property should be marked as optional" : ""))
                return
            }
            if Thread.current.isMainThread {
                closure(typedValue)
            } else {
                DispatchQueue.main.async {
                    closure(typedValue)
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
}

extension Publisher {
    public func first() -> Publisher {
        return PublisherExtensionsKt.first(self)
    }
}
