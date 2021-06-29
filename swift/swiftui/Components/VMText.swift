import SwiftUI
import TRIKOT_FRAMEWORK_NAME

public struct VMText: View {
    public typealias Body = Text

    private let viewModel: TextViewModel
    private let letterSpacing: CGFloat
    private let underline: Bool
    @ObservedObject private var observableViewModel: ObservableViewModelAdapter<TextViewModel>

    public init(_ viewModel: TextViewModel, letterSpacing: CGFloat = 0, underline: Bool = false) {
        self.viewModel = viewModel
        self.observableViewModel = viewModel.asObservable()
        self.letterSpacing = letterSpacing
        self.underline = underline
    }

    public var body: Text {
        var text: Text = Text(viewModel.text)

        if letterSpacing != 0 {
            text = text.kerning(letterSpacing)
        }

        if underline {
            text = text.underline()
        }

        return text
    }
}
