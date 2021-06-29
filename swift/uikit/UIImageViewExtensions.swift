import Kingfisher
import TRIKOT_FRAMEWORK_NAME
import UIKit

private let IMAGE_VIEW_MODEL_HANDLER_KEY = UnsafeMutablePointer<Int8>.allocate(capacity: 1)
private let SAVED_CONTENT_MODE_KEY = UnsafeMutablePointer<Int8>.allocate(capacity: 1)
private let PLACEHOLDER_CONTENT_MODE_KEY = UnsafeMutablePointer<Int8>.allocate(capacity: 1)

extension UIImageView: ViewModelDeclarativeCompatible { }

extension ViewModelDeclarativeWrapper where Base : UIImageView {
    public var imageViewModel: ImageViewModel? {
        get { return base.vmd.getViewModel() }
        set(value) {
            viewModel = value
            ViewModelDeclarativeWrapper.imageViewModelHandler.handleImage(imageViewModel: value, on: base)
        }
    }

    public static var imageViewModelHandler: ImageViewModelHandler {
        get {
            if let customHandler = objc_getAssociatedObject(self, IMAGE_VIEW_MODEL_HANDLER_KEY) as? ImageViewModelHandler {
                return customHandler
            } else {
                return DefaultImageViewModelHandler.shared
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

public protocol ImageViewModelHandler {
    func handleImage(imageViewModel: ImageViewModel?, on imageView: UIImageView)
}

private class DefaultImageViewModelHandler: ImageViewModelHandler {
    static let shared = DefaultImageViewModelHandler()

    private init() { }

    public func handleImage(imageViewModel: ImageViewModel?, on imageView: UIImageView) {
        if let imageViewModel = imageViewModel {
            imageView.vmd.observe(imageViewModel.publisher(for: \ImageViewModel.image)) { [weak imageView] imageDescriptor in
                imageView?.vmd.restoreContentMode()
                if let local = imageDescriptor as? ImageDescriptor.Local {
                    imageView?.image = local.imageResource.uiImage
                } else if let remote = imageDescriptor as? ImageDescriptor.Remote {
                    if let placeholderContentMode = imageView?.vmd.placeholderContentMode {
                        imageView?.vmd.saveContentMode()
                        imageView?.contentMode = placeholderContentMode
                    }
                    let placeholderImage = remote.placeholderImageResource.uiImage
                    if let url = URL(string: remote.url) {
                        imageView?.image = nil
                        let imageResource = Kingfisher.ImageResource(downloadURL: url)
                        imageView?.kf.setImage(with: imageResource, placeholder: placeholderImage, completionHandler: { [weak imageView] result in
                            switch result {
                            case .success:
                                imageView?.vmd.restoreContentMode()
                            case .failure:
                                imageView?.image = placeholderImage
                            }
                        })
                    } else {
                        imageView?.image = placeholderImage
                    }
                }
            }
        } else {
            imageView.image = nil
        }
    }
}
