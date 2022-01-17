import Kingfisher
import TRIKOT_FRAMEWORK_NAME
import UIKit

private let IMAGE_VIEW_MODEL_HANDLER_KEY = UnsafeMutablePointer<Int8>.allocate(capacity: 1)
private let SAVED_CONTENT_MODE_KEY = UnsafeMutablePointer<Int8>.allocate(capacity: 1)
private let PLACEHOLDER_CONTENT_MODE_KEY = UnsafeMutablePointer<Int8>.allocate(capacity: 1)

extension ViewModelDeclarativeWrapper where Base : UIImageView {
    public var imageViewModel: VMDImageViewModel? {
        get { return base.vmd.getViewModel() }
        set(value) {
            viewModel = value
            ViewModelDeclarativeWrapper.imageViewModelHandler.handleImage(imageViewModel: value, on: base)
        }
    }

    public static var imageViewModelHandler: VMDImageViewModelHandler {
        get {
            if let customHandler = objc_getAssociatedObject(self, IMAGE_VIEW_MODEL_HANDLER_KEY) as? VMDImageViewModelHandler {
                return customHandler
            } else {
                return VMDDefaultImageViewModelHandler.shared
            }
        }
        set(value) {
            objc_setAssociatedObject(self, IMAGE_VIEW_MODEL_HANDLER_KEY, value, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN)
        }
    }

    public var placeholderContentMode: UIView.ContentMode? {
        get {
            return objc_getAssociatedObject(self, PLACEHOLDER_CONTENT_MODE_KEY) as? UIView.ContentMode
        }
        set(value) {
            objc_setAssociatedObject(self, PLACEHOLDER_CONTENT_MODE_KEY, value, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN)
        }
    }

    fileprivate var savedContentMode: UIView.ContentMode? {
        get {
            return objc_getAssociatedObject(self, SAVED_CONTENT_MODE_KEY) as? UIView.ContentMode
        }
        set(value) {
            objc_setAssociatedObject(self, SAVED_CONTENT_MODE_KEY, value, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN)
        }
    }

    public func saveContentMode() {
        base.vmd.savedContentMode = base.contentMode
    }

    public func restoreContentMode() {
        if let savedContentMode = base.vmd.savedContentMode, base.contentMode != savedContentMode {
            base.contentMode = savedContentMode
        }
        base.vmd.savedContentMode = nil
    }
}

public protocol VMDImageViewModelHandler {
    func handleImage(imageViewModel: VMDImageViewModel?, on imageView: UIImageView)
    func handleImageDescriptor(imageDescriptor: VMDImageDescriptor?, on imageView: UIImageView)
}

private class VMDDefaultImageViewModelHandler: VMDImageViewModelHandler {
    static let shared = VMDDefaultImageViewModelHandler()

    private init() { }

    public func handleImage(imageViewModel: VMDImageViewModel?, on imageView: UIImageView) {
        if let imageViewModel = imageViewModel {
            imageView.vmd.observe(imageViewModel.publisher(for: \VMDImageViewModel.image)) { [weak imageView, weak self] imageDescriptor in
                if let imageView = imageView {
                    self?.handleImageDescriptor(imageDescriptor: imageDescriptor, on: imageView)
                }
            }
        } else {
            imageView.image = nil
        }
    }

    public func handleImageDescriptor(imageDescriptor: VMDImageDescriptor?, on imageView: UIImageView) {
        if let imageDescriptor = imageDescriptor {
            imageView.vmd.restoreContentMode()
            if let local = imageDescriptor as? VMDImageDescriptor.Local {
                imageView.image = local.imageResource.uiImage
            } else if let remote = imageDescriptor as? VMDImageDescriptor.Remote {
                if let placeholderContentMode = imageView.vmd.placeholderContentMode {
                    imageView.vmd.saveContentMode()
                    imageView.contentMode = placeholderContentMode
                }
                let placeholderImage = remote.placeholderImageResource.uiImage
                if let url = URL(string: remote.url) {
                    imageView.image = nil
                    let imageResource = Kingfisher.ImageResource(downloadURL: url)
                    imageView.kf.setImage(with: imageResource, placeholder: placeholderImage, completionHandler: { [weak imageView] result in
                        switch result {
                        case .success:
                            imageView?.vmd.restoreContentMode()
                        case .failure:
                            imageView?.image = placeholderImage
                        }
                    })
                } else {
                    imageView.image = placeholderImage
                }
            }
        } else {
            imageView.image = nil
        }
    }
}
