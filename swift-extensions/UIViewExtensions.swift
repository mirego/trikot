import UIKit
import TRIKOT_FRAMEWORK_NAME
import Trikot_streams

extension UIView {
    public var metaView: MetaView? {
        get { return trikotMetaView() }
        set(metaView) {
            unsubscribeFromAllPublisher()
            setTrikotMetaView(metaView: metaView)
            guard let metaView = metaView else { return }

            bind(metaView.alpha, \UIView.alpha)

            bindColorSelectorDefaultValue(metaView.backgroundColor, \UIView.backgroundColor)

            bind(metaView.hidden, \UIView.isHidden)

            let onTapResetableCancelableManager = CancellableManagerProvider()
            trikotInternalPublisherCancellableManager.add(cancellable: onTapResetableCancelableManager)

            if !(self is UIControl) {
                observe(metaView.onTap) {[weak self] (value: MetaAction) in
                    guard let self = self else { return }
                    let newCancellableManager = onTapResetableCancelableManager.cancelPreviousAndCreate()

                    if value != MetaAction.Companion().None {
                        let tapGestureReconizer = UITapGestureRecognizer(target: self, action: #selector(self.trikotOnViewTouchUp))
                        self.addGestureRecognizer(tapGestureReconizer)
                        newCancellableManager.add {[weak self] in
                            self?.removeGestureRecognizer(tapGestureReconizer)
                        }
                    }
                }
            }
        }
    }

    private struct AssociatedKeys {
        static var metaViewKey = UnsafeMutablePointer<Int8>.allocate(capacity: 1)
    }

    public func trikotMetaView<T>() -> T? {
        return objc_getAssociatedObject(self, AssociatedKeys.metaViewKey) as? T
    }

    public func setTrikotMetaView<T>(metaView: T?) {
        objc_setAssociatedObject(self, AssociatedKeys.metaViewKey, metaView, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN_NONATOMIC)
    }

    @objc
    private func trikotOnViewTouchUp() {
        let localMetaView: MetaView? = trikotMetaView()
        guard let metaMetaView = localMetaView else { return }
        observe(metaMetaView.onTap.first()) {[weak self] (value: MetaAction) in value.execute(actionContext: self) }
    }
}
