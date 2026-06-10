import SwiftUI
import TRIKOT_FRAMEWORK_NAME

public protocol VMDImageProvider {
    func imageForResource(imageResource: VMDImageResource) -> Image?
}

public class TrikotViewModelDeclarative {

    public static let shared: TrikotViewModelDeclarative = TrikotViewModelDeclarative()

    var imageProvider: VMDImageProvider {
        guard let internalImageProvider = internalImageProvider else {
            fatalError("TrikotViewModelDeclarative must be initialized before use")
        }

        return internalImageProvider
    }

    private var internalImageProvider: VMDImageProvider?

    private init() {
    }

    public func initialize(imageProvider: VMDImageProvider) {
        internalImageProvider = imageProvider
    }
}
