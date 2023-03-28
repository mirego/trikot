import TRIKOT_FRAMEWORK_NAME

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
        imageProvider: VMDImageProvider = DefaultImageProvider(),
        spanStyleProvider: VMDSpanStyleProvider = DefaultSpanStyleProvider()
    ) {
        internalImageProvider = imageProvider
        internalSpanStyleProvider = spanStyleProvider
    }
}
