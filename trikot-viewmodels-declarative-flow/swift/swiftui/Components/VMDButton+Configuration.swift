import SwiftUI
import SampleTrikotFrameworkName

public extension VMDButton {
    func externalActionHandler(_ action: @escaping (_ action: () -> Void) -> Void) -> VMDButton {
        var button = self
        button.externalActionHandler = action
        return button
    }
}
