import UIKit
import TRIKOT_FRAMEWORK_NAME

public struct TextAppearanceAttributes {
    let font: UIFont
    let foregroundColor: UIColor
    let backgroundColor: UIColor

    public init(font: UIFont, foregroundColor: UIColor, backgroundColor: UIColor = .clear) {
        self.font = font
        self.foregroundColor = foregroundColor
        self.backgroundColor = backgroundColor
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
