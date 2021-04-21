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

            observe(PublisherExtensionsKt.distinctUntilChanged(toggleSwitchViewModel.isOn)) { [weak self] (value: Bool) in
                self?.isOn = value
            }

            bind(toggleSwitchViewModel.isEnabled, \UISwitch.isEnabled)
            
            observe(toggleSwitchViewModel.toggleSwitchAction) {[weak self] (value: ViewModelAction) in
                guard let self = self else { return }
                self.removePreviousRegisteredTapAction()
                if value != ViewModelAction.Companion().None {
                    self.addTarget(self, action: #selector(UISwitch.onValueChanged), for: .valueChanged)
                }
            }
        }
    }
    
    private func removePreviousRegisteredTapAction() {
        self.removeTarget(self, action: nil, for: .valueChanged)
    }
    
    @objc
    func onValueChanged(sender: UISwitch) {
        if #available(iOS 10.0, *), let style = impactFeedbackStyle() {
            UIImpactFeedbackGenerator(style: getFeedbackStyle(style: style)).impactOccurred()
        }
        guard let toggleSwitchViewModel = toggleSwitchViewModel else { return }
        observe(toggleSwitchViewModel.toggleSwitchAction.first()) {[weak self] (value: ViewModelAction) in value.execute(actionContext: self) }
        PromiseCompanion().from(single: toggleSwitchViewModel.isOn, cancellableManager: nil).onSuccess(accept: { value in
            let isOn = (value as! Bool)
            if self.isOn != isOn {
                self.setOn(isOn, animated: true)
            }
        })
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
