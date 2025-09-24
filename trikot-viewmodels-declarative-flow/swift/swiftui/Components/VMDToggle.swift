import SwiftUI
import Jasper

public struct VMDToggle<Label, Content>: View where Label: View, Content: VMDContent {
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

    public init(_ viewModel: VMDToggleViewModel<Content>, @ViewBuilder label: @escaping (Content) -> Label) {
        self.observableViewModel = viewModel.asObservable()
        self.labelBuilder = label
        self.title = nil
    }

    public var body: some View {
        if let title = title {
            if title.isEmpty {
                Toggle(title, isOn: isOn)
                    .labelsHidden()
                    .disabled(!viewModel.isEnabled)
                    .vmdModifier(isHidden: viewModel.isHidden, testIdentifier: viewModel.testIdentifier)
            } else {
                Toggle(title, isOn: isOn)
                    .disabled(!viewModel.isEnabled)
                    .vmdModifier(isHidden: viewModel.isHidden, testIdentifier: viewModel.testIdentifier)
            }
        } else {
            Toggle(isOn: isOn) {
                labelBuilder?(viewModel.label)
            }
            .disabled(!viewModel.isEnabled)
            .vmdModifier(isHidden: viewModel.isHidden, testIdentifier: viewModel.testIdentifier)
        }
    }
}

extension VMDToggle where Content == VMDNoContent, Label == EmptyView {
    public init(_ viewModel: VMDToggleViewModel<VMDNoContent>) {
        self.observableViewModel = viewModel.asObservable()
        self.labelBuilder = nil
        self.title = ""
    }
}

extension VMDToggle where Content == VMDTextContent, Label == Text {
    public init(_ viewModel: VMDToggleViewModel<VMDTextContent>) {
        self.observableViewModel = viewModel.asObservable()
        self.labelBuilder = nil
        self.title = viewModel.label.text
    }
}
