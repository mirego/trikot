import UIKit
import TRIKOT_FRAMEWORK_NAME

extension UISlider {
    private struct AssosiatedKeys {
        static var oldValue = UnsafeMutablePointer<Int8>.allocate(capacity: 1)
        static var impactFeedbackStyle = UnsafeMutablePointer<Int8>.allocate(capacity: 1)
    }

    public var sliderViewModel: SliderViewModel? {
        get { return trikotViewModel() }
        set(value) {
            if oldValue() == nil { oldValue(value: 0) }
            if impactFeedbackStyle() == nil { impactFeedbackStyle(value: .light) }

            viewModel = value
            guard let sliderViewModel = value else { return }
            self.minimumValue = Float(sliderViewModel.minValue)
            self.maximumValue = Float(sliderViewModel.maxValue)

            self.addTarget(self, action: #selector(UISlider.onValueChanged), for: .valueChanged)
            observe(sliderViewModel.selectedValue) { [weak self] (value: Int32) in
                self?.value = Float(value)
            }
        }
    }

    @objc
    func onValueChanged(sender: UISlider) {
        guard let integerValue = Int32(String(format: "%.0f", self.value)) else { return }

        if integerValue != oldValue() {
            if #available(iOS 10.0, *), let style = impactFeedbackStyle() {
                UIImpactFeedbackGenerator(style: getFeedbackStyle(style: style)).impactOccurred()
            }
            oldValue(value: integerValue)
            sliderViewModel?.setSelectedValue(value: integerValue)
        }
    }

    private func oldValue() -> Int32? {
        return objc_getAssociatedObject(self, AssosiatedKeys.oldValue) as? Int32
    }

    private func oldValue(value: Int32) {
        objc_setAssociatedObject(self, AssosiatedKeys.oldValue, value, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN_NONATOMIC)
    }

    private func impactFeedbackStyle() -> FeedbackStyle? {
        return objc_getAssociatedObject(self, AssosiatedKeys.impactFeedbackStyle) as? FeedbackStyle
    }

    private func impactFeedbackStyle(value: FeedbackStyle?) {
        objc_setAssociatedObject(self, AssosiatedKeys.impactFeedbackStyle, value, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN_NONATOMIC)
    }
    
    public enum FeedbackStyle {
        case light
        case medium
        case heavy
        case soft
        case rigid
    }
    
    @available(iOS 10.0, *)
    private func getFeedbackStyle(style: FeedbackStyle) -> UIImpactFeedbackGenerator.FeedbackStyle {
        switch style {
        case .light:
            return .light
        case .medium:
            return .medium
        case .heavy:
            return .heavy
        case .soft:
            if #available(iOS 13.0, *) {
                return .soft
            } else {
                return .light
            }
        case .rigid:
            if #available(iOS 13.0, *) {
                return .rigid
            } else {
                return .heavy
            }
        }
    }
}
