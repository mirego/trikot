import TRIKOT_FRAMEWORK_NAME
import UIKit

public protocol ViewModelDeclarativeCompatible : AnyObject {
}

extension ViewModelDeclarativeCompatible {
    public var vmd: ViewModelDeclarativeWrapper<Self> {
        get { return ViewModelDeclarativeWrapper(self) }
        set { }
    }
}

public struct ViewModelDeclarativeWrapper<Base> {
    public let base: Base
    public init(_ base: Base) {
        self.base = base
    }
}

private enum AssociatedKeys {
    static var declarativeViewModelKey = UnsafeMutablePointer<Int8>.allocate(capacity: 1)
}

extension ViewModelDeclarativeWrapper where Base : NSObject {

    public func getViewModel<T>() -> T? {
        return objc_getAssociatedObject(base, AssociatedKeys.declarativeViewModelKey) as? T
    }

    public func setViewModel<T>(viewModel: T?) {
        objc_setAssociatedObject(base, AssociatedKeys.declarativeViewModelKey, viewModel, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN_NONATOMIC)
    }

    public func observe<V>(_ publisher: PublisherAdapter<V>, toClosure closure: @escaping ((V) -> Void)) {
        base.observe(cancellableManager: base.trikotInternalPublisherCancellableManager, publisher: publisher, toClosure: closure)
    }

    public func observe<V>(cancellableManager: CancellableManager, publisher: PublisherAdapter<V>, toClosure closure: @escaping ((V) -> Void)) {
        PublisherExtensionsKt.subscribe(publisher, cancellableManager: cancellableManager) {(value: Any?) in
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

    public func bind<T: NSObject, V>(publisher: PublisherAdapter<V>, _ keyPath: ReferenceWritableKeyPath<T, V>) {
        guard let base = base as? T else { return }
        base.observe(publisher) { [weak base] (newValue: V) in
            base?[keyPath: keyPath] = newValue
        }
    }

    public func bind<T: NSObject, V, I>(publisher: PublisherAdapter<I>, _ keyPath: ReferenceWritableKeyPath<T, V>, transform: @escaping ((I) -> V)) {
        guard let base = base as? T else { return }
        base.observe(publisher) { [weak base] (newValue: I) in
            base?[keyPath: keyPath] = transform(newValue)
        }
    }
}
