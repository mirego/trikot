import SwiftUI
import TRIKOT_FRAMEWORK_NAME

public struct VMDButton<Label, Content: VMDContent>: View where Label: View {
    private let labelBuilder: (Content) -> Label

    var externalActionHandler: ((_ action: () -> Void) -> Void)?

    @ObservedObject private var observableViewModel: ObservableViewModelAdapter<VMDButtonViewModel<Content>>

    private var viewModel: VMDButtonViewModel<Content> {
        observableViewModel.viewModel
    }

    public init(_ viewModel: VMDButtonViewModel<Content>, @ViewBuilder label: @escaping (Content) -> Label) {
        self.observableViewModel = viewModel.asObservable()
        self.labelBuilder = label
    }

    public var body: some View {
        Button {
            if let externalActionHandler = externalActionHandler {
                externalActionHandler {
                    viewModel.actionBlock()
                }
            } else {
                viewModel.actionBlock()
            }
        } label: {
            labelBuilder(viewModel.content)
        }
        .disabled(!viewModel.isEnabled)
        .hidden(viewModel.isHidden)
    }
}
