import UIKit
import TRIKOT_FRAMEWORK_NAME

extension Color {
    public func safeColor() -> UIColor? {
        return self == Color.Companion().None ? nil : UIColor(red: CGFloat(red) / 255, green: CGFloat(green) / 255, blue: CGFloat(blue) / 255, alpha: CGFloat(alpha))
    }

    public func color() -> UIColor {
        return safeColor()!
    }
}
