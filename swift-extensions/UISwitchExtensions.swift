import UIKit
import TRIKOT_FRAMEWORK_NAME

extension UISwitch {

    private struct AssosiatedKeys {
        static var impactFeedbackStyle = UnsafeMutablePointer<Int8>.allocate(capacity: 1)
    }
    
    public var toggleSwitchViewModel: ToggleSwitchViewModel? {
        get { return trikotViewModel() }
        set(value) {
            if impactFeedbackStyle() == nil { impactFeedbackStyle(value: .light) }
            
            viewModel = value
            guard let toggleSwitchViewModel = value else { return }

            self.addTarget(self, action: #selector(UISwitch.onValueChanged), for: .valueChanged)
            observe(toggleSwitchViewModel.isOn) { [weak self] (value: Bool) in
                self?.isOn = value
            }

            bind(toggleSwitchViewModel.enabled, \UISwitch.isEnabled)
        }
    }

    @objc
    func onValueChanged(sender: UISwitch) {
        if #available(iOS 10.0, *), let style = impactFeedbackStyle() {
            UIImpactFeedbackGenerator(style: getFeedbackStyle(style: style)).impactOccurred()
        }
        toggleSwitchViewModel?.setIsOn(on: self.isOn)
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
