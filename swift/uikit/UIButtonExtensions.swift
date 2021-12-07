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
            bindAction(buttonViewModel.actionBlock)
        }
    }

    func bindWithImageViewModel(_ viewModel: VMDButtonViewModel<VMDImageContent>?) {
        removeBindAction()
        if let buttonViewModel = viewModel {
            vmd.observe(buttonViewModel.publisher(for: \VMDButtonViewModel<VMDImageContent>.content)) { [weak self] content in
                guard let strongSelf = self else { return }
                self?.vmd.buttonViewModelImageHandler.setImageDescriptor(content.image, for: .normal, on: strongSelf)
            }
            bindAction(buttonViewModel.actionBlock)
        }
    }

    func bindWithTextImageViewModel(_ viewModel: VMDButtonViewModel<VMDTextImagePairContent>?) {
        removeBindAction()
        if let buttonViewModel = viewModel {
            vmd.observe(buttonViewModel.publisher(for: \VMDButtonViewModel<VMDTextImagePairContent>.content)) { [weak self] content in
                guard let strongSelf = self else { return }
                strongSelf.setTitle(content.text, for: .normal)
                strongSelf.vmd.buttonViewModelImageHandler.setImageDescriptor(content.image, for: .normal, on: strongSelf)
            }
            bindAction(buttonViewModel.actionBlock)
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

public protocol ButtonViewModelImageHandler {
    func setImageDescriptor(_ imageDescriptor: VMDImageDescriptor?, for state: UIControl.State, on button: UIButton)
}

private class DefaultButtonViewModelHandler: ButtonViewModelImageHandler {
    static let shared = DefaultButtonViewModelHandler()

    private init() { }

    public func setImageDescriptor(_ imageDescriptor: VMDImageDescriptor?, for state: UIControl.State, on button: UIButton) {
        if let local = imageDescriptor as? VMDImageDescriptor.Local {
            button.setImage(local.imageResource.uiImage, for: state)
        } else if let remote = imageDescriptor as? VMDImageDescriptor.Remote, let imageURL = URL(string: remote.url) {
            let placeholderImage = remote.placeholderImageResource.uiImage
            button.kf.setImage(with: imageURL, for: state, placeholder: placeholderImage, options: [.onFailureImage(placeholderImage)])
        } else {
            button.setImage(nil, for: state)
        }
    }
}
