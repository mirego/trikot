import SwiftUI
import TRIKOT_FRAMEWORK_NAME

public struct VMDText: View {
    public typealias TextConfiguration = (Text) -> Text

    private var viewModel: VMDTextViewModel {
        observableViewModel.viewModel
    }

    @ObservedObject private var observableViewModel: ObservableViewModelAdapter<VMDTextViewModel>

    private var configurations = [TextConfiguration]()

    public init(_ viewModel: VMDTextViewModel) {
        self.observableViewModel = viewModel.asObservable()
    }

    public var body: some View {
        Group {
            if viewModel.spans.isEmpty {
                configurations.reduce(Text(viewModel.text)) { current, config in
                    config(current)
                }
            } else {
                if #available(iOS 15, *) {
                    Text(AttributedString(text: viewModel.text, spans: viewModel.spans))
                } else {
                    UIRichText(observableViewModel: observableViewModel)
                }
            }
        }
        .hidden(viewModel.isHidden)
    }

    public func configure(_ block: @escaping TextConfiguration) -> VMDText {
        var result = self
        result.configurations.append(block)
        return result
    }
}

@available(iOS, obsoleted: 15, message: "Use Text.init(_ attributedContent: AttributedString)")
private struct UIRichText: UIViewRepresentable {
    @ObservedObject var observableViewModel: ObservableViewModelAdapter<VMDTextViewModel>

    private var viewModel: VMDTextViewModel {
        observableViewModel.viewModel
    }

    func makeUIView(context: Context) -> UILabel {
        let label = UILabel()
        label.attributedText = NSAttributedString(text: viewModel.text, spans: viewModel.spans)
        return label
    }

    func updateUIView(_ label: UILabel, context: Context) {
        label.attributedText = NSAttributedString(text: viewModel.text, spans: viewModel.spans)
    }
}
