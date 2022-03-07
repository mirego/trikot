import SwiftUI
import TRIKOT_FRAMEWORK_NAME

public struct VMDProgressView<Label, CurrentValueLabel>: View where Label : View, CurrentValueLabel : View {
    public typealias ProgressViewConfiguration = (ProgressView<Label, CurrentValueLabel>) -> ProgressView<Label, CurrentValueLabel>

    private var viewModel: VMDProgressViewModel {
        observableViewModel.viewModel
    }

    @ObservedObject private var observableViewModel: ObservableViewModelAdapter<VMDProgressViewModel>

    private var configurations = [ProgressViewConfiguration]()
    private let labelBuilder: (() -> Label)?
    private let currentValueLabelBuilder: (() -> CurrentValueLabel)?

    public func configure(_ block: @escaping ProgressViewConfiguration) -> VMDProgressView {
        var result = self
        result.configurations.append(block)
        return result
    }

    public var body: some View {
        if let determination = viewModel.determination,
            let labelBuilder = labelBuilder,
            let currentValueLabelBuilder = currentValueLabelBuilder {

            return configurations.reduce(ProgressView(value: determination.progress, total: determination.total, label: labelBuilder, currentValueLabel: currentValueLabelBuilder)) { current, config in
                config(current)
            }
            .hidden(viewModel.isHidden)
        } else if let labelBuilder = labelBuilder {
            if let determination = viewModel.determination {
                return configurations.reduce(ProgressView(value: determination.progressRatio, label: labelBuilder) as! ProgressView<Label, CurrentValueLabel>) { current, config in
                    config(current)
                }
                .hidden(viewModel.isHidden)
            } else {
                return configurations.reduce(ProgressView(label: labelBuilder) as! ProgressView<Label, CurrentValueLabel>) { current, config in
                    config(current)
                }
                .hidden(viewModel.isHidden)
            }
        } else {
            if let determination = viewModel.determination {
                return configurations.reduce(ProgressView(value: determination.progressRatio) as! ProgressView<Label, CurrentValueLabel>) { current, config in
                    config(current)
                }
                .hidden(viewModel.isHidden)
            } else {
                return configurations.reduce(ProgressView() as! ProgressView<Label, CurrentValueLabel>) { current, config in
                    config(current)
                }
                .hidden(viewModel.isHidden)
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
