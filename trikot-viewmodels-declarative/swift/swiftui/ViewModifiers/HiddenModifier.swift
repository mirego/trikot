import SwiftUI

struct HiddenModifier: ViewModifier {
    var hidden: Bool

    func body(content: Content) -> some View {
        if hidden {
            content.hidden()
        } else {
            content
        }
    }
}

extension View {
    public func hidden(_ hidden: Bool) -> some View {
        modifier(HiddenModifier(hidden: hidden))
    }
}
