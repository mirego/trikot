import UIKit
import Jasper

public struct TextAppearanceAttributes {
    public let attributes: [NSAttributedString.Key: Any]

    public init(attributes: [NSAttributedString.Key: Any] = [:]) {
        self.attributes = attributes
    }
}

public protocol TextAppearanceViewModelResourceProvider {
    func textAppearance(fromResource resource: TextAppearanceResource) -> TextAppearanceAttributes?
}

class DefaultTextAppearanceViewModelResourceProvider: TextAppearanceViewModelResourceProvider {
    func textAppearance(fromResource resource: TextAppearanceResource) -> TextAppearanceAttributes? {
        nil
    }
}

public class TextAppearanceViewModelResourceManager {
    public static var shared: TextAppearanceViewModelResourceProvider = DefaultTextAppearanceViewModelResourceProvider()
}
