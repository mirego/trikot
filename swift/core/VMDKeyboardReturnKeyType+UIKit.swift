import UIKit
import TRIKOT_FRAMEWORK_NAME

extension VMDKeyboardReturnKeyType {
    public var uiReturnKeyType: UIReturnKeyType {
        switch self {
        case .default_:
            return .default
        case .done:
            return .done
        case .go:
            return .go
        case .next:
            return .next
        case .search:
            return .search
        case .send:
            return .send
        default:
            return .default
        }
    }
}
