import SwiftUI
import TRIKOT_FRAMEWORK_NAME


public class TrikotViewModelDeclarative {
    public static let shared: TrikotViewModelDeclarative = TrikotViewModelDeclarative()

    var isRunningInPreview: Bool {
        ProcessInfo.processInfo.environment["XCODE_RUNNING_FOR_PREVIEWS"] == "1"
    }

    var imageProvider: VMDImageProvider {
        guard let internalImageProvider = internalImageProvider else {
            guard isRunningInPreview else { fatalError("TrikotViewModelDeclarative must be initialized before use") }
            return FallbackImageProvider()
        }

        return internalImageProvider
    }

    var spanStyleProvider: VMDSpanStyleProvider {
        guard let internalSpanStyleProvider = internalSpanStyleProvider else {
            guard isRunningInPreview else { fatalError("TrikotViewModelDeclarative must be initialized before use") }
            return DefaultSpanStyleProvider()
        }

        return internalSpanStyleProvider
    }

    private var internalImageProvider: VMDImageProvider?

    private var internalSpanStyleProvider: VMDSpanStyleProvider?

    private init() {
    }

    public func initialize(
        imageProvider: VMDImageProvider = DefaultImageProvider(),
        spanStyleProvider: VMDSpanStyleProvider = DefaultSpanStyleProvider()
    ) {
        internalImageProvider = imageProvider
        internalSpanStyleProvider = spanStyleProvider
    }
}

private class FallbackImageProvider: VMDImageProvider {
    func imageForResource(imageResource: VMDImageResource) -> Image? {
        return Image(uiImage: UIImage(color: .red, size: CGSize(width: 12, height: 12))!)
    }
}

private extension UIImage {
    convenience init?(color: UIColor, size: CGSize = CGSize(width: 1, height: 1)) {
        let rect = CGRect(origin: .zero, size: size)
        UIGraphicsBeginImageContextWithOptions(rect.size, false, 0.0)
        if let currentContext = UIGraphicsGetCurrentContext() {
            color.setFill()
            currentContext.fillEllipse(in: rect.insetBy(dx: size.width / 4, dy: size.height / 4))

            UIColor.black.setStroke()
            UIRectFrame(rect)
            let image = UIGraphicsGetImageFromCurrentImageContext()
            UIGraphicsEndImageContext()
            guard let cgImage = image?.cgImage else { return nil }
            self.init(cgImage: cgImage)
        } else {
            return nil
        }
    }
}
