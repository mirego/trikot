import UIKit
import TRIKOT_FRAMEWORK_NAME

private let IMAGE_VIEW_MODEL_HANDLER_KEY = UnsafeMutablePointer<Int8>.allocate(capacity: 1)
private let USER_CONTENT_MODE_KEY = UnsafeMutablePointer<Int8>.allocate(capacity: 1)
private let USER_PLACEHOLDER_CONTENT_MODE_KEY = UnsafeMutablePointer<Int8>.allocate(capacity: 1)
private let USER_IMAGERESOURCE_CONTENT_MODE_KEY = UnsafeMutablePointer<Int8>.allocate(capacity: 1)

public protocol ImageViewModelHandler {
    func handleImage(imageViewModel: ImageViewModel?, on imageView: UIImageView)
}

extension UIImageView {
    public static var imageViewModelHandler: ImageViewModelHandler {
        set(value) {
            objc_setAssociatedObject(self, IMAGE_VIEW_MODEL_HANDLER_KEY, value, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN)
        }
        get {
            if let customHandler = objc_getAssociatedObject(self, IMAGE_VIEW_MODEL_HANDLER_KEY) as? ImageViewModelHandler {
                return customHandler
            } else {
                return DefaultImageViewModelHandler.shared
            }
        }
    }

    @available(*, deprecated, renamed: "contentMode")
    public var imageResourceContentMode: ContentMode {
        set(value) {
            contentMode = value
        }
        get {
            return contentMode
        }
    }

    public var placeholderContentMode: ContentMode? {
        set(value) {
            objc_setAssociatedObject(self, USER_PLACEHOLDER_CONTENT_MODE_KEY, value, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN)
        }
        get {
            return objc_getAssociatedObject(self, USER_PLACEHOLDER_CONTENT_MODE_KEY) as? ContentMode
        }
    }

    private var savedContentMode: ContentMode? {
        set(value) {
            objc_setAssociatedObject(self, USER_CONTENT_MODE_KEY, value, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN)
        }
        get {
            return objc_getAssociatedObject(self, USER_CONTENT_MODE_KEY) as? ContentMode
        }
    }

    public var imageViewModel: ImageViewModel? {
        get { return trikotViewModel() }
        set(value) {
            viewModel = value
            UIImageView.imageViewModelHandler.handleImage(imageViewModel: value, on: self)
        }
    }

    public func saveContentMode() {
        savedContentMode = contentMode
    }

    public func restoreContentMode() {
        if let savedContentMode = savedContentMode {
            contentMode = savedContentMode
        } else {
            savedContentMode = nil
        }
    }
}

public class DefaultImageViewModelHandler: ImageViewModelHandler {
    public static let shared = DefaultImageViewModelHandler()
    private let imageCache = NSCache<NSString, UIImage>()

    public var isImageCacheEnabled = true
    public var isImageDependantOnViewSize = false

    public var maxNumberOfImagesInCache: Int {
        get {
            return imageCache.countLimit
        }
        set {
            imageCache.countLimit = newValue
        }
    }

    public var maxNumberOfImagePixelsInCache: Int {
        get {
            return imageCache.totalCostLimit
        }
        set {
            imageCache.totalCostLimit = newValue
        }
    }

    private init() { }

    public func clearImageCache() {
        imageCache.removeAllObjects()
    }

    public func handleImage(imageViewModel: ImageViewModel?, on imageView: UIImageView) {
        if let imageViewModel = imageViewModel {
            let cancellableManagerProvider = CancellableManagerProvider()

            let imageFlowPublisher = imageViewModel.imageFlow(width: Int32(imageView.frame.width * UIScreen.main.scale), height: Int32(imageView.frame.height * UIScreen.main.scale))

            imageView.restoreContentMode()
            observeImageFlow(imageFlowPublisher, cancellableManager: cancellableManagerProvider.cancelPreviousAndCreate(), imageViewModel: imageViewModel, imageView: imageView)

            if isImageDependantOnViewSize {
                let sizeObservationCancellation = KeyValueObservationHolder(imageView.observe(\UIImageView.bounds, options: [.old, .new]) {[weak self] (_, change) in
                    if change.newValue?.size != change.oldValue?.size { self?.observeImageFlow(imageFlowPublisher, cancellableManager: cancellableManagerProvider.cancelPreviousAndCreate(), imageViewModel: imageViewModel, imageView: imageView) }
                })

                imageView.trikotInternalPublisherCancellableManager.add(cancellable: sizeObservationCancellation)
            }

            imageView.trikotInternalPublisherCancellableManager.add(cancellable: cancellableManagerProvider)
        } else {
            imageView.trikotInternalPublisherCancellableManager.cancel()
        }
    }

    private func observeImageFlow(_ imageFlowPublisher: Publisher, cancellableManager: CancellableManager, imageViewModel: ImageViewModel, imageView: UIImageView) {
        let cancellableManagerProvider = CancellableManagerProvider()
        cancellableManager.add(cancellable: cancellableManagerProvider)

        imageView.observe(cancellableManager: cancellableManager, publisher: imageFlowPublisher) {[weak self] (imageFlow: ImageFlow) in
            self?.doLoadImageFlow(cancellableManager: cancellableManagerProvider.cancelPreviousAndCreate(), imageViewModel: imageViewModel, imageFlow: imageFlow, imageView: imageView)
        }
    }

    private func doLoadImageFlow(cancellableManager: CancellableManager, imageViewModel: ImageViewModel, imageFlow: ImageFlow, imageView: UIImageView) {
        var unProcessedImage: UIImage?
        if let imageResource = imageFlow.imageResource {
            unProcessedImage = ImageViewModelResourceManager.shared.image(fromResource: imageResource)
            imageViewModel.setImageState(imageState: ImageState.success)
        }
        if let imageResource = imageFlow.placeholderImageResource {
            if let placeholderContentMode = imageView.placeholderContentMode {
                imageView.saveContentMode()
                imageView.contentMode = placeholderContentMode
            }
            unProcessedImage = ImageViewModelResourceManager.shared.image(fromResource: imageResource)
        }
        if let unProcessedImage = unProcessedImage {
            if let tintColor = imageFlow.tintColor {
                imageView.image = unProcessedImage.imageWithTintColor(tintColor.color())
            } else {
                imageView.image = unProcessedImage
            }
        }

        downloadImageFlowIfNeeded(cancellableManager: cancellableManager, imageViewModel: imageViewModel, imageFlow: imageFlow, imageView: imageView)
    }

    private func downloadImageFlowIfNeeded(cancellableManager: CancellableManager, imageViewModel: ImageViewModel, imageFlow: ImageFlow, imageView: UIImageView) {
        guard let urlString = imageFlow.url, let url = URL(string: urlString) else { return }

        if isImageCacheEnabled, let cachedImage = imageCache.object(forKey: url.absoluteString as NSString) {
            imageView.restoreContentMode()
            imageView.image = cachedImage

            if let onSuccess = imageFlow.onSuccess {
                observeImageFlow(onSuccess, cancellableManager: cancellableManager, imageViewModel: imageViewModel, imageView: imageView)
            }
        } else {
            MrFreeze().freeze(objectToFreeze: cancellableManager)

            let dataTask = URLSession.shared.dataTask(with: url) { [weak imageView, weak self] data, response, error in
                DispatchQueue.main.async {
                    guard let imageView = imageView else { return }
                    if let httpURLResponse = response as? HTTPURLResponse, httpURLResponse.statusCode == 200,
                        let mimeType = response?.mimeType, mimeType.hasPrefix("image"),
                        let data = data, error == nil,
                        let image = UIImage(data: data) {

                        self?.imageCache.setObject(image, forKey: url.absoluteString as NSString, cost: image.cacheCost)
                        imageView.restoreContentMode()
                        imageView.image = image

                        if let onSuccess = imageFlow.onSuccess {
                            self?.observeImageFlow(onSuccess, cancellableManager: cancellableManager, imageViewModel: imageViewModel, imageView: imageView)
                        } else {
                            imageViewModel.setImageState(imageState: ImageState.success)
                        }
                    } else if let onError = imageFlow.onError {
                        self?.observeImageFlow(onError, cancellableManager: cancellableManager, imageViewModel: imageViewModel, imageView: imageView)
                    } else {
                        imageViewModel.setImageState(imageState: ImageState.error)
                    }
                }
            }
            cancellableManager.add(cancellable: dataTask)
            dataTask.resume()
        }
    }
}

extension URLSessionTask: Cancellable {}

private extension UIImage {
    var cacheCost: Int {
        get {
            return Int(size.width * size.height)
        }
    }
}
