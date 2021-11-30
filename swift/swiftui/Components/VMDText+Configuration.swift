import SwiftUI

public extension VMDText {
    func foregroundColor(_ color: Color?) -> VMDText {
        configure { $0.foregroundColor(color) }
    }

    func kerning(_ kerning: CGFloat) -> VMDText {
        configure { $0.kerning(kerning) }
    }

    func tracking(_ tracking: CGFloat) -> VMDText {
        configure { $0.tracking(tracking) }
    }

    func underline() -> VMDText {
        configure { $0.underline() }
    }

    func strikethrough(_ active: Bool = true, color: Color? = nil) -> VMDText {
        configure { $0.strikethrough(active, color: color) }
    }

    func bold() -> VMDText {
        configure { $0.bold() }
    }

    func italic() -> VMDText {
        configure { $0.italic() }
    }

    func font(_ font: Font?) -> VMDText {
        configure { $0.font(font) }
    }

    func fontWeight(_ weight: Font.Weight?) -> VMDText {
        configure { $0.fontWeight(weight) }
    }
}
