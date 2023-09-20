import SwiftUI
import TRIKOT_FRAMEWORK_NAME

struct VmdModifier: ViewModifier {
    let isHidden: Bool
    let testIdentifier: String?

    func body(content: Content) -> some View {
        content
            .hidden(isHidden)
            .applyIfNotNull(testIdentifier) { view, testIdentifier in
                view.accessibilityIdentifier(testIdentifier)
            }
    }
}

public extension View {
    func vmdModifier(isHidden: Bool, testIdentifier: String?) -> some View {
        modifier(VmdModifier(isHidden: isHidden, testIdentifier: testIdentifier))
    }

    @ViewBuilder
    internal func applyIfNotNull<Value: Any, Content: View>(_ value: Value?, block: (Self, Value) -> Content) -> some View {
         if let value {
             block(self, value)
         } else {
             self
         }
     }
}
