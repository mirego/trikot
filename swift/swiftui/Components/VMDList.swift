import SwiftUI
import TRIKOT_FRAMEWORK_NAME

public struct VMDList<RowContent, ContentData>: View where RowContent: View, ContentData: VMDIdentifiableContent {
    private let rowContentBuilder: (ContentData) -> RowContent

    @ObservedObject private var observableViewModel: ObservableViewModelAdapter<VMDListViewModel<ContentData>>

    var viewModel: VMDListViewModel<ContentData> {
        observableViewModel.viewModel
    }

    init(_ viewModel: VMDListViewModel<ContentData>, @ViewBuilder rowContentBuilder: @escaping (ContentData) -> RowContent) {
        self.observableViewModel = viewModel.asObservable()
        self.rowContentBuilder = rowContentBuilder
    }

    public var body: some View {
        List(viewModel.elements, id: \ContentData.identifier) {
            rowContentBuilder($0)
        }
        .hidden(viewModel.isHidden)
    }
}
