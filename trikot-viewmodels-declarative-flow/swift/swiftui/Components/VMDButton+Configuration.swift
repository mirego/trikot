import SwiftUI
import TrikotFrameworkName

public extension VMDButton {
    func externalActionHandler(_ action: @escaping (_ action: () -> Void) -> Void) -> VMDButton {
        var button = self
        button.externalActionHandler = action
        return button
    }
}
