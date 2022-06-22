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

    func font(_ font: Font?) -> VMDText {
        configure { $0.font(font) }
    }

    func fontWeight(_ weight: Font.Weight?) -> VMDText {
        configure { $0.fontWeight(weight) }
    }

    func ultraLight() -> VMDText {
        configure { $0.fontWeight(.ultraLight) }
    }

    func thin() -> VMDText {
        configure { $0.fontWeight(.thin) }
    }

    func regular() -> VMDText {
        configure { $0.fontWeight(.regular) }
    }

    func medium() -> VMDText {
        configure { $0.fontWeight(.medium) }
    }

    func semibold() -> VMDText {
        configure { $0.fontWeight(.semibold) }
    }

    func bold() -> VMDText {
        configure { $0.fontWeight(.bold) }
    }

    func heavy() -> VMDText {
        configure { $0.fontWeight(.heavy) }
    }

    func black() -> VMDText {
        configure { $0.fontWeight(.black) }
    }

    func italic() -> VMDText {
        configure { $0.italic() }
    }
}
