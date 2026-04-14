import SwiftUI
import SampleTrikotFrameworkName

public protocol VMDImageProvider {
    func imageForResource(imageResource: VMDImageResource) -> Image?
}

public struct DefaultImageProvider: VMDImageProvider {
    public init() { }
    public func imageForResource(imageResource: VMDImageResource) -> Image? { nil }
}
