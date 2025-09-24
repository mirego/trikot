import SwiftUI
import Jasper

public struct VMDProgressView<Label, CurrentValueLabel>: View where Label : View, CurrentValueLabel : View {
    private var viewModel: VMDProgressViewModel {
        observableViewModel.viewModel
    }

    @ObservedObject private var observableViewModel: ObservableViewModelAdapter<VMDProgressViewModel>

    private let labelBuilder: (() -> Label)?
    private let currentValueLabelBuilder: (() -> CurrentValueLabel)?

    public var body: some View {
        if let determination = viewModel.determination,
            let labelBuilder = labelBuilder,
            let currentValueLabelBuilder = currentValueLabelBuilder {

            ProgressView(value: determination.progress, total: determination.total, label: labelBuilder, currentValueLabel: currentValueLabelBuilder)
                .vmdModifier(isHidden: viewModel.isHidden, testIdentifier: viewModel.testIdentifier)
        } else if let labelBuilder = labelBuilder {
            if let determination = viewModel.determination {
                ProgressView(value: determination.progressRatio, label: labelBuilder)
                    .vmdModifier(isHidden: viewModel.isHidden, testIdentifier: viewModel.testIdentifier)
            } else {
                ProgressView(label: labelBuilder)
                    .vmdModifier(isHidden: viewModel.isHidden, testIdentifier: viewModel.testIdentifier)
            }
        } else {
            if let determination = viewModel.determination {
                ProgressView(value: determination.progressRatio)
                    .vmdModifier(isHidden: viewModel.isHidden, testIdentifier: viewModel.testIdentifier)
            } else {
                ProgressView()
                    .vmdModifier(isHidden: viewModel.isHidden, testIdentifier: viewModel.testIdentifier)
            }
        }
    }
}

extension VMDProgressView where Label == EmptyView, CurrentValueLabel == EmptyView {
    public init(_ viewModel: VMDProgressViewModel) {
        self.observableViewModel = viewModel.asObservable()
        self.labelBuilder = nil
        self.currentValueLabelBuilder = nil
    }
}

extension VMDProgressView where Label: View, CurrentValueLabel == EmptyView {
    public init(_ viewModel: VMDProgressViewModel, @ViewBuilder label: @escaping () -> Label) {
        self.observableViewModel = viewModel.asObservable()
        self.labelBuilder = label
        self.currentValueLabelBuilder = nil
    }
}

extension VMDProgressView where Label: View, CurrentValueLabel: View {
    public init(_ viewModel: VMDProgressViewModel, @ViewBuilder label: @escaping () -> Label, @ViewBuilder currentValueLabel: @escaping () -> CurrentValueLabel) {
        self.observableViewModel = viewModel.asObservable()
        self.labelBuilder = label
        self.currentValueLabelBuilder = currentValueLabel
    }
}
