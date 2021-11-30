import SwiftUI
import TRIKOT_FRAMEWORK_NAME

public struct VMDSwitch<Label, Content: VMDContent>: View where Label: View {
    private let labelBuilder: ((Content) -> Label)?
    private let title: String?

    @ObservedObject private var observableViewModel: ObservableViewModelAdapter<VMDToggleViewModel<Content>>

    private var viewModel: VMDToggleViewModel<Content> {
        observableViewModel.viewModel
    }

    private var isOn: Binding<Bool> {
        Binding {
            self.viewModel.isOn
        } set: { isOn in
            self.viewModel.onValueChange(isOn: isOn)
        }
    }

    public init(_ viewModel: VMDToggleViewModel<VMDNoContent>) {
        self.observableViewModel = viewModel.asObservable()
        self.labelBuilder = nil
        self.title = ""
    }

    public init(_ viewModel: VMDToggleViewModel<VMDTextContent>) {
        self.observableViewModel = viewModel.asObservable()
        self.labelBuilder = nil
        self.title = viewModel.label.text
    }

    public init(_ viewModel: VMDToggleViewModel<Content>, @ViewBuilder label: @escaping (Content) -> Label) {
        self.observableViewModel = viewModel.asObservable()
        self.labelBuilder = label
        self.title = nil
    }

    public var body: some View {
        if let title = title {
            Toggle(title, isOn: isOn)
                .disabled(!viewModel.enabled)
                .hidden(viewModel.isHidden)
        } else {
            Toggle(isOn: isOn) {
                labelBuilder?(viewModel.label)
            }
            .disabled(!viewModel.enabled)
            .hidden(viewModel.isHidden)
        }
    }
}
