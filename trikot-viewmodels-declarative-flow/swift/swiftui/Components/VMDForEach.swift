import SwiftUI
import Jasper
import Trikot

public struct VMDForEach<RowContent, Identifiable, Content, DividerContent>: DynamicViewContent where RowContent: View, DividerContent: View, Identifiable: VMDIdentifiableContent, Content: VMDContent {
    private let rowContentBuilder: (Content) -> RowContent
    private let dividedBy: () -> DividerContent

    @ObservedObject private var observableViewModel: ObservableViewModelAdapter<VMDListViewModel<Identifiable>>

    public var data: [VMDIdentifiableContent] {
        viewModel.elements
    }

    private var viewModel: VMDListViewModel<Identifiable> {
        observableViewModel.viewModel
    }

    public init(_ viewModel: VMDListViewModel<Identifiable>, @ViewBuilder dividedBy: @escaping () -> DividerContent, @ViewBuilder rowContentBuilder: @escaping (Content) -> RowContent) where Identifiable == Content {
        self.observableViewModel = viewModel.asObservable()
        self.rowContentBuilder = rowContentBuilder
        self.dividedBy = dividedBy
    }

    public init(_ viewModel: VMDListViewModel<Identifiable>, @ViewBuilder rowContentBuilder: @escaping (Content) -> RowContent) where Identifiable == Content, DividerContent == EmptyView {
        self.observableViewModel = viewModel.asObservable()
        self.rowContentBuilder = rowContentBuilder
        self.dividedBy = { EmptyView() }
    }

    public init(_ viewModel: VMDListViewModel<Identifiable>, @ViewBuilder rowContentBuilder: @escaping (Content) -> RowContent) where Identifiable: VMDIdentifiableContentAbstract<Content>, DividerContent == EmptyView {
        self.observableViewModel = viewModel.asObservable()
        self.rowContentBuilder = rowContentBuilder
        self.dividedBy = { EmptyView() }
    }

    public var body: some View {
        ForEach(viewModel.elements, id: \Identifiable.identifier) {
            if let content = $0 as? Content {
                rowContentBuilder(content)

                if $0.identifier != viewModel.elements.last?.identifier {
                    dividedBy()
                }
            }
        }
        .vmdModifier(isHidden: viewModel.isHidden, testIdentifier: viewModel.testIdentifier)
    }
}
