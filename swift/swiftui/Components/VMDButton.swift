import SwiftUI
import TRIKOT_FRAMEWORK_NAME

public struct VMDButton<Label, Content: VMDContent>: View where Label: View {
    private let labelBuilder: (Content) -> Label

    @ObservedObject private var observableViewModel: ObservableViewModelAdapter<VMDButtonViewModel<Content>>

    var viewModel: VMDButtonViewModel<Content> {
        observableViewModel.viewModel
    }

    init(_ viewModel: VMDButtonViewModel<Content>, @ViewBuilder label: @escaping (Content) -> Label) {
        self.observableViewModel = viewModel.asObservable()
        self.labelBuilder = label
    }

    public var body: some View {
        Button(action: {
            self.viewModel.action()
        }, label: {
            labelBuilder(viewModel.content)
        })
            .disabled(!viewModel.enabled)
            .hidden(viewModel.isHidden)
    }
}
