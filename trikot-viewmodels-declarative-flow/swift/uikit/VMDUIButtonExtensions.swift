import Kingfisher
import TRIKOT_FRAMEWORK_NAME
import UIKit

private let BUTTON_VIEW_MODEL_IMAGE_HANDLER_KEY = UnsafeMutablePointer<Int8>.allocate(capacity: 1)

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

    public var buttonViewModelImageHandler: ButtonViewModelImageHandler {
        get {
            if let customHandler = objc_getAssociatedObject(self, BUTTON_VIEW_MODEL_IMAGE_HANDLER_KEY) as? ButtonViewModelImageHandler {
                return customHandler
            } else {
                return DefaultButtonViewModelHandler.shared
            }
        }
        set(value) {
            objc_setAssociatedObject(self, BUTTON_VIEW_MODEL_IMAGE_HANDLER_KEY, value, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN)
        }
    }

    public func bindActionBlock(_ action: @escaping () -> Void) {
        base.bindActionBlock(action)
    }

    public func unbindActionBlock() {
        base.unbindActionBlock()
    }
}

fileprivate extension UIButton {
    func bindWithTextViewModel(_ viewModel: VMDButtonViewModel<VMDTextContent>?) {
        unbindActionBlock()
        if let buttonViewModel = viewModel {
            vmd.observe(buttonViewModel.publisher(for: \VMDButtonViewModel<VMDTextContent>.content)) { [weak self] content in
                self?.setTitle(content.text, for: .normal)
            }
            bindActionBlock(buttonViewModel.actionBlock)
        }
    }

    func bindWithImageViewModel(_ viewModel: VMDButtonViewModel<VMDImageContent>?) {
        unbindActionBlock()
        if let buttonViewModel = viewModel {
            vmd.observe(buttonViewModel.publisher(for: \VMDButtonViewModel<VMDImageContent>.content)) { [weak self] content in
                guard let strongSelf = self else { return }
                self?.vmd.buttonViewModelImageHandler.setImage(content.image, for: .normal, on: strongSelf)
            }
            bindActionBlock(buttonViewModel.actionBlock)
        }
    }

    func bindWithTextImageViewModel(_ viewModel: VMDButtonViewModel<VMDTextImagePairContent>?) {
        unbindActionBlock()
        if let buttonViewModel = viewModel {
            vmd.observe(buttonViewModel.publisher(for: \VMDButtonViewModel<VMDTextImagePairContent>.content)) { [weak self] content in
                guard let strongSelf = self else { return }
                strongSelf.setTitle(content.text, for: .normal)
                strongSelf.vmd.buttonViewModelImageHandler.setImage(content.image, for: .normal, on: strongSelf)
            }
            bindActionBlock(buttonViewModel.actionBlock)
        }
    }

    private enum AssociatedKeys {
        static var actionBlockKey = UnsafeMutablePointer<Int8>.allocate(capacity: 1)
    }

    private var actionBlock: (() -> Void)? {
        get {
            return objc_getAssociatedObject(self, AssociatedKeys.actionBlockKey) as? (() -> Void)
        }
        set {
            objc_setAssociatedObject(self, AssociatedKeys.actionBlockKey, newValue, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN)
        }
    }

    func unbindActionBlock() {
        actionBlock = nil
        removeTarget(self, action: #selector(onActionTriggered), for: .primaryActionTriggered)
    }

    func bindActionBlock(_ action: @escaping () -> Void) {
        actionBlock = action
        addTarget(self, action: #selector(onActionTriggered), for: .primaryActionTriggered)
    }

    @objc
    private func onActionTriggered() {
        actionBlock?()
    }
}

public protocol ButtonViewModelImageHandler {
    func setImage(_ imageResource: VMDImageResource?, for state: UIControl.State, on button: UIButton)
}

private class DefaultButtonViewModelHandler: ButtonViewModelImageHandler {
    static let shared = DefaultButtonViewModelHandler()

    private init() { }

    public func setImage(_ imageResource: VMDImageResource?, for state: UIControl.State, on button: UIButton) {
        if let imageResource = imageResource {
            button.setImage(imageResource.uiImage, for: state)
        } else {
            button.setImage(nil, for: state)
        }
    }
}
