import TRIKOT_FRAMEWORK_NAME
import UIKit

extension ViewModelDeclarativeWrapper where Base : UIButton {
    public var buttonWithTextViewModel: VMDButtonViewModel<VMDTextContent>? {
        get { return base.vmd.getViewModel() }
        set(value) {
            controlViewModel = value
            base.bindWithTextViewModel(value)
        }
    }

    public var buttonWithImageViewModel: VMDButtonViewModel<VMDImageContent>? {
        get { return base.vmd.getViewModel() }
        set(value) {
            controlViewModel = value
            base.bindWithImageViewModel(value)
        }
    }

    public var buttonWithTextImageViewModel: VMDButtonViewModel<VMDTextImagePairContent>? {
        get { return base.vmd.getViewModel() }
        set(value) {
            controlViewModel = value
            base.bindWithTextImageViewModel(value)
        }
    }

    public func removeBindAction() {
        base.removeBindAction()
    }

    public func bindAction(_ action: @escaping () -> Void) {
        base.bindAction(action)
    }
}

fileprivate extension UIButton {
    func bindWithTextViewModel(_ viewModel: VMDButtonViewModel<VMDTextContent>?) {
        removeBindAction()
        if let buttonViewModel = viewModel {
            vmd.observe(buttonViewModel.publisher(for: \VMDButtonViewModel<VMDTextContent>.content)) { [weak self] content in
                self?.setTitle(content.text, for: .normal)
            }
            bindAction(buttonViewModel.action)
        }
    }

    func bindWithImageViewModel(_ viewModel: VMDButtonViewModel<VMDImageContent>?) {
        removeBindAction()
        if let buttonViewModel = viewModel {
            vmd.observe(buttonViewModel.publisher(for: \VMDButtonViewModel<VMDImageContent>.content)) { [weak self] content in
                self?.setImage(content.image.uiImage, for: .normal)
            }
            bindAction(buttonViewModel.action)
        }
    }

    func bindWithTextImageViewModel(_ viewModel: VMDButtonViewModel<VMDTextImagePairContent>?) {
        removeBindAction()
        if let buttonViewModel = viewModel {
            vmd.observe(buttonViewModel.publisher(for: \VMDButtonViewModel<VMDTextImagePairContent>.content)) { [weak self] content in
                self?.setTitle(content.text, for: .normal)
                self?.setImage(content.image.uiImage, for: .normal)
            }
            bindAction(buttonViewModel.action)
        }
    }

    private enum AssociatedKeys {
        static var actionKey = UnsafeMutablePointer<Int8>.allocate(capacity: 1)
    }

    private var tapAction: (() -> Void)? {
        get {
            return objc_getAssociatedObject(self, AssociatedKeys.actionKey) as? (() -> Void)
        }
        set {
            objc_setAssociatedObject(self, AssociatedKeys.actionKey, newValue, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN)
        }
    }

    func removeBindAction() {
        tapAction = nil
        removeTarget(self, action: #selector(onPrimaryActionTriggered), for: .primaryActionTriggered)
    }

    func bindAction(_ action: @escaping () -> Void) {
        tapAction = action
        addTarget(self, action: #selector(onPrimaryActionTriggered), for: .primaryActionTriggered)
    }

    @objc
    private func onPrimaryActionTriggered() {
        tapAction?()
    }
}
