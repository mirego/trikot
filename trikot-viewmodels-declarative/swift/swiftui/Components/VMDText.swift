import SwiftUI
import Jasper

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
        configurations.reduce(Text(viewModel.text)) { current, config in
            config(current)
        }
        .hidden(viewModel.isHidden)
    }

    public func configure(_ block: @escaping TextConfiguration) -> VMDText {
        var result = self
        result.configurations.append(block)
        return result
    }
}
