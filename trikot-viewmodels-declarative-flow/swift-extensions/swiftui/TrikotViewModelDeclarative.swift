import SwiftUI
import Trikot
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
        return Image(systemName: "exclamationmark.brakesignal")
    }
}
