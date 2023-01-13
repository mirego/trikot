import SwiftUI
import TRIKOT_FRAMEWORK_NAME

public extension VMDButton {
    func externalActionHandler(_ action: @escaping (_ action: () -> Void) -> Void) -> VMDButton {
        var button = self
        button.externalActionHandler = action
        return button
    }
}
