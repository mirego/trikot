import SwiftUI

fileprivate struct PreviewPadding: ViewModifier {
    private let layout: PreviewLayout
    private var shouldAddPadding: Bool {
        switch layout {
        case .device: return true
        default: return false
        }
    }

    init(layout: PreviewLayout) {
        self.layout = layout
    }

    func body(content: Content) -> some View {
        Group {
            if shouldAddPadding {
                content
            } else {
                content.padding()
            }
        }
    }
}

extension View {
    func previewPadding(_ layout: PreviewLayout) -> some View {
        modifier(PreviewPadding(layout: layout))
    }
}
