import UIKit
import TRIKOT_FRAMEWORK_NAME

extension VMDKeyboardType {
    var uiKeyboardType: UIKeyboardType {
        switch self {
        case .default_:
            return .default
        case .ascii:
            return .asciiCapable
        case .number:
            return .numberPad
        case .email:
            return .emailAddress
        case .password:
            return .default
        case .numberpassword:
            return .numberPad
        case .phone:
            return .phonePad
        case .url:
            return .URL
        default:
            return .default
        }
    }
}
