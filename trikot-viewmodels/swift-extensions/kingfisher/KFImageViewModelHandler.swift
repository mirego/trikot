import UIKit
import Kingfisher
import TRIKOT_FRAMEWORK_NAME

public protocol KFImageUrlRequestModifierDelegate: AnyObject {
    func requestModifier(for url: URL) -> ImageDownloadRequestModifier
}

public class KFImageViewModelHandler: ImageViewModelHandler {

    public weak var delegate: KFImageUrlRequestModifierDelegate?

    public var isImageDependantOnViewSize = false
    public var sizeMultiplier: CGFloat = UIScreen.main.scale

    public init() {}

    public func handleImage(imageViewModel: ImageViewModel?, on imageView: UIImageView) {
        guard let imageViewModel = imageViewModel else {
            imageView.unsubscribeFromAllPublisher()
            return
        }

        imageView.image = nil
        imageView.restoreContentMode()

        let imageFlowPublisher = imageViewModel.imageFlow(
            width: ImageWidth(value: Int32(imageView.frame.width * sizeMultiplier)),
            height: ImageHeight(value: Int32(imageView.frame.height * sizeMultiplier))
        )

        let cancellableManagerProvider = CancellableManagerProvider()

        observeImageFlow(
            imageFlowPublisher,
            cancellableManager: cancellableManagerProvider.cancelPreviousAndCreate(),
            imageViewModel: imageViewModel,
            imageView: imageView
        )

        imageView.trikotInternalPublisherCancellableManager.add(cancellable: cancellableManagerProvider)
    }

    private func observeImageFlow(
        _ imageFlowPublisher: Publisher,
        cancellableManager: CancellableManager,
        imageViewModel: ImageViewModel,
        imageView: UIImageView
    ) {
        let cancellableManagerProvider = CancellableManagerProvider()
        cancellableManager.add(cancellable: cancellableManagerProvider)

        imageView.observe(cancellableManager: cancellableManager, publisher: imageFlowPublisher) {[weak self] (imageFlow: ImageFlow) in
            self?.doLoadImageFlow(
                cancellableManager: cancellableManagerProvider.cancelPreviousAndCreate(),
                imageViewModel: imageViewModel,
                imageFlow: imageFlow,
                imageView: imageView
            )
        }
    }

    private func doLoadImageFlow(
        cancellableManager: CancellableManager,
        imageViewModel: ImageViewModel,
        imageFlow: ImageFlow,
        imageView: UIImageView
    ) {
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

        downloadImageFlowIfNeeded(
            cancellableManager: cancellableManager,
            imageViewModel: imageViewModel,
            imageFlow: imageFlow,
            imageView: imageView
        )
    }

    private func downloadImageFlowIfNeeded(
        cancellableManager: CancellableManager,
        imageViewModel: ImageViewModel,
        imageFlow: ImageFlow,
        imageView: UIImageView
    ) {
        guard let urlString = imageFlow.url, let url = URL(string: urlString) else { return }

        var options: KingfisherOptionsInfo = []
        if let modifier = delegate?.requestModifier(for: url) {
            options.append(.requestModifier(modifier))
        }

        let task = imageView.kf.setImage(with: url, options: options, completionHandler: { [weak self] result in
            MrFreeze().freeze(objectToFreeze: cancellableManager)
            MrFreeze().freeze(objectToFreeze: imageFlow)

            switch result {
            case .success:
                if let onSuccess = imageFlow.onSuccess {
                    self?.observeImageFlow(
                        onSuccess,
                        cancellableManager: cancellableManager,
                        imageViewModel: imageViewModel,
                        imageView: imageView
                    )
                } else {
                    imageViewModel.setImageState(imageState: ImageState.success)
                }
            case .failure:
                if let onError = imageFlow.onError {
                    self?.observeImageFlow(
                        onError,
                        cancellableManager: cancellableManager,
                        imageViewModel: imageViewModel,
                        imageView: imageView
                    )
                } else {
                    imageViewModel.setImageState(imageState: ImageState.error)
                }
            }
        })

        cancellableManager.add { task?.cancel() }
    }
}
