import SwiftUI
import SampleTrikotFrameworkName

extension VMDKeyboardReturnKeyType {
    public var submitLabel: SubmitLabel {
        switch self {
        case .default_:
            return .done
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
            return .done
        }
    }
}
