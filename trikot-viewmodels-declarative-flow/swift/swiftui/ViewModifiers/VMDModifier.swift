import SwiftUI
import TRIKOT_FRAMEWORK_NAME

struct VmdModifier: ViewModifier {
    var viewModel: VMDViewModel
    
    func body(content: Content) -> some View {
        content
            .hidden(viewModel.isHidden)
            .applyIfNotNull(viewModel.testIdentifier) { view, testIdentifier in
                view.accessibilityIdentifier(testIdentifier)
            }
    }
}

public extension View {
    func vmdModifier(_ viewModel: VMDViewModel) -> some View {
        modifier(VmdModifier(viewModel: viewModel))
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
