import SwiftUI
import TRIKOT_FRAMEWORK_NAME

public struct VMDList<RowContent, Identifiable, Content>: View where RowContent: View, Identifiable: VMDIdentifiableContent, Content: VMDContent {
    private let rowContentBuilder: (Content) -> RowContent

    @ObservedObject private var observableViewModel: ObservableViewModelAdapter<VMDListViewModel<Identifiable>>

    private var viewModel: VMDListViewModel<Identifiable> {
        observableViewModel.viewModel
    }

    public init(_ viewModel: VMDListViewModel<Identifiable>, @ViewBuilder rowContentBuilder: @escaping (Content) -> RowContent) where Identifiable == Content {
        self.observableViewModel = viewModel.asObservable()
        self.rowContentBuilder = rowContentBuilder
    }

    public init(_ viewModel: VMDListViewModel<Identifiable>, @ViewBuilder rowContentBuilder: @escaping (Content) -> RowContent) where Identifiable: VMDIdentifiableContentAbstract<Content> {
        self.observableViewModel = viewModel.asObservable()
        self.rowContentBuilder = rowContentBuilder
    }

    public var body: some View {
        List(viewModel.elements, id: \Identifiable.identifier) {
            if let identifiableContent = $0 as? VMDIdentifiableContentAbstract<Content> {
                rowContentBuilder(identifiableContent.content)
            } else if let content = $0 as? Content {
                rowContentBuilder(content)
            }
        }
        .hidden(viewModel.isHidden)
    }
}
