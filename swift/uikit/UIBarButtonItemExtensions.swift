import TRIKOT_FRAMEWORK_NAME
import UIKit

extension UIBarButtonItem: ViewModelDeclarativeCompatible { }

extension ViewModelDeclarativeWrapper where Base : UIBarButtonItem {
    public var barButtonWithImageViewModel: ButtonViewModel<ImageContent>? {
        get { return base.vmd.getViewModel() }
        set(value) {
            base.unsubscribeFromAllPublisher()
            base.vmd.setViewModel(viewModel: value)
            base.bindViewModel(value)
        }
    }
}

fileprivate extension UIBarButtonItem {
    func bindViewModel(_ viewModel: ButtonViewModel<ImageContent>?) {
        removeBindAction()
        if let buttonViewModel = viewModel {
            vmd.observe(buttonViewModel.publisher(for: \ButtonViewModel<ImageContent>.content)) { [weak self] content in
                self?.image = content.image.uiImage
            }
            bindAction(buttonViewModel.action)
        }
    }

    private enum AssociatedKeys {
        static var actionKey = UnsafeMutablePointer<Int8>.allocate(capacity: 1)
    }

     var tapAction: (() -> Void)? {
        get {
            return objc_getAssociatedObject(self, AssociatedKeys.actionKey) as? (() -> Void)
        }
        set {
            objc_setAssociatedObject(self, AssociatedKeys.actionKey, newValue, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN)
        }
    }

    @objc
    func onButtonTapped() {
        tapAction?()
    }

    func removeBindAction() {
        tapAction = nil
        target = nil
        action = nil
    }

    func bindAction(_ action: @escaping () -> Void) {
        tapAction = action
        target = self
        self.action = #selector(UIBarButtonItem.onButtonTapped)
    }
}
