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

    var spanStyleProvider: VMDSpanStyleProvider {
        guard let internalSpanStyleProvider = internalSpanStyleProvider else {
            fatalError("TrikotViewModelDeclarative must be initialized before use")
        }

        return internalSpanStyleProvider
    }

    private var internalImageProvider: VMDImageProvider?

    private var internalSpanStyleProvider: VMDSpanStyleProvider?

    private init() {
    }

    public func initialize(
        imageProvider: VMDImageProvider,
        spanStyleProvider: VMDSpanStyleProvider
    ) {
        internalImageProvider = imageProvider
        internalSpanStyleProvider = spanStyleProvider
    }
}
