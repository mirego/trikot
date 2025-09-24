import SwiftUI
import Jasper

public struct VMDPicker<Label, Content: VMDContent>: View where Label: View {
    private let labelBuilder: (Content) -> Label

    @ObservedObject var observableViewModel: ObservableViewModelAdapter<VMDPickerViewModel<VMDContentPickerItemViewModelImpl<Content>>>
    @ObservedObject var observableLabelViewModel: ObservableViewModelAdapter<VMDTextViewModel>

    public init(_ viewModel: VMDPickerViewModel<VMDContentPickerItemViewModelImpl<Content>>, label labelViewModel: VMDTextViewModel, @ViewBuilder label: @escaping (Content) -> Label) {
        observableViewModel = viewModel.asObservable()
        observableLabelViewModel = labelViewModel.asObservable()
        self.labelBuilder = label
    }

    private var viewModel: VMDPickerViewModel<VMDContentPickerItemViewModelImpl<Content>> {
        observableViewModel.viewModel
    }

    private var labelViewModel: VMDTextViewModel {
        observableLabelViewModel.viewModel
    }

    public var body: some View {
        Picker(labelViewModel.text, selection: Binding(
            get: { Int(viewModel.selectedIndex) },
            set: { index in
                guard index != VMDPickerViewModelImpl<AnyObject>.companion.NO_SELECTION else { return }
                viewModel.selectedIndex = Int32(index)
            }
        )) {
            ForEach(Array(viewModel.elements.enumerated()), id: \.element.identifier) { index, item in
                labelBuilder(item.content)
                    .tag(index)
            }
        }
        .vmdModifier(isHidden: viewModel.isHidden, testIdentifier: viewModel.testIdentifier)
    }
}
