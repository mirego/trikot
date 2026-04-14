import SwiftUI

struct FocusModifier: ViewModifier {

    @FocusState var focused: Bool
    @Binding var state: Bool

    init(_ state: Binding<Bool>){
        self._state = state
    }

    func body(content: Content) -> some View {
        content.focused($focused, equals: true)
            .onChange(of: state, perform: changeFocus)
    }

    private func changeFocus(_ value: Bool){
        focused = value
    }
}

extension View{
    func focusMe(_ state: Binding<Bool>) -> some View {
        self.modifier(FocusModifier(state))
    }
}
