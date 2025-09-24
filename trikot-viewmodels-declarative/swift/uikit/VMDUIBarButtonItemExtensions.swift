import Kingfisher
import Jasper
import UIKit

extension UIBarButtonItem: ViewModelDeclarativeCompatible { }

extension ViewModelDeclarativeWrapper where Base : UIBarButtonItem {
    public var barButtonWithImageViewModel: VMDButtonViewModel<VMDImageContent>? {
        get { return base.vmd.getViewModel() }
        set(value) {
            base.unsubscribeFromAllPublisher()
            base.vmd.setViewModel(viewModel: value)
            base.bindViewModel(value)
        }
    }
}

fileprivate extension UIBarButtonItem {
    func bindViewModel(_ viewModel: VMDButtonViewModel<VMDImageContent>?) {
        unbindActionBlock()
        if let buttonViewModel = viewModel {
            vmd.observe(buttonViewModel.publisher(for: \VMDButtonViewModel<VMDImageContent>.content)) { [weak self] content in
                self?.image = content.image.uiImage
            }
            bindActionBlock(buttonViewModel.actionBlock)
        }
    }

    private enum AssociatedKeys {
        static var actionBlockKey = UnsafeMutablePointer<Int8>.allocate(capacity: 1)
    }

     var actionBlock: (() -> Void)? {
        get {
            return objc_getAssociatedObject(self, AssociatedKeys.actionBlockKey) as? (() -> Void)
        }
        set {
            objc_setAssociatedObject(self, AssociatedKeys.actionBlockKey, newValue, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN)
        }
    }

    func bindActionBlock(_ actionBlock: @escaping () -> Void) {
        self.actionBlock = actionBlock
        target = self
        action = #selector(UIBarButtonItem.onActionTriggered)
    }

    func unbindActionBlock() {
        actionBlock = nil
        target = nil
        action = nil
    }

    @objc
    private func onActionTriggered() {
        actionBlock?()
    }
}
