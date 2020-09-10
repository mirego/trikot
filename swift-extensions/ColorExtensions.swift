import UIKit
import ViewModelsSample

extension Color {
    public func safeColor() -> UIColor? {
        return self == Color.Companion().None ? nil : UIColor(red: CGFloat(red) / 255, green: CGFloat(green) / 255, blue: CGFloat(blue) / 255, alpha: CGFloat(alpha))
    }

    public func color() -> UIColor {
        return safeColor()!
    }
}

extension NSObject {
    public func bindColorSelectorDefaultValue<T>(_ publisher: Publisher, _ keyPath: ReferenceWritableKeyPath<T, UIColor?>) {
        observe(publisher) {[weak self] (newValue: StateSelector<Color>) in
            guard let strongSelf = self as? T else { return }
            if let newColor = (newValue.defaultValue() as Color?)?.safeColor() {
                strongSelf[keyPath: keyPath] = newColor
            }
        }
    }

    public func bindColor<T>(_ publisher: Publisher, _ keyPath: ReferenceWritableKeyPath<T, UIColor?>) {
        observe(publisher) {[weak self] (color: Color) in
            guard let strongSelf = self as? T else { return }
            if let newColor = color.safeColor() {
                strongSelf[keyPath: keyPath] = newColor
            }
        }
    }
}
