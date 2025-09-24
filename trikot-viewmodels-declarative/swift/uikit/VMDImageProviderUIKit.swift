import UIKit
import Jasper

public protocol VMDImageProviderUIKit {
    func imageForResource(imageResource: VMDImageResource) -> UIImage?
}

public class TrikotViewModelDeclarativeUIKit {

    public static let shared: TrikotViewModelDeclarativeUIKit = TrikotViewModelDeclarativeUIKit()

    var imageProvider: VMDImageProviderUIKit {
        guard let internalImageProvider = internalImageProvider else {
            fatalError("TrikotViewModelDeclarative must be initialized before use")
        }

        return internalImageProvider
    }

    private var internalImageProvider: VMDImageProviderUIKit?

    private init() {
    }

    public func initialize(imageProvider: VMDImageProviderUIKit) {
        internalImageProvider = imageProvider
    }
}
