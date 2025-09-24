import SwiftUI
import Jasper

public extension VMDButton {
    func externalActionHandler(_ action: @escaping (_ action: () -> Void) -> Void) -> VMDButton {
        var button = self
        button.externalActionHandler = action
        return button
    }
}
