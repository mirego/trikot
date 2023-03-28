import SwiftUI
import TRIKOT_FRAMEWORK_NAME

public protocol VMDImageProvider {
    func imageForResource(imageResource: VMDImageResource) -> Image?
}

public struct DefaultImageProvider: VMDImageProvider {
    public init() { }
    public func imageForResource(imageResource: VMDImageResource) -> Image? { nil }
}
