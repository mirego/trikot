import UIKit
import TRIKOT_FRAMEWORK_NAME

public extension VMDKeyboardAutoCapitalization {
    public var uiTextAutocapitalizationType: UITextAutocapitalizationType {
        switch self {
        case .none:
            return .none
        case .characters:
            return .allCharacters
        case .sentences:
            return .sentences
        case .words:
            return .words
        default:
            return .none
        }
    }
}
