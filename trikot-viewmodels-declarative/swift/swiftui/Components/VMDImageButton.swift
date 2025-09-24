import SwiftUI
import Jasper

public struct VMDImageButton<Content: VMDImageContent>: View {
    
    @ObservedObject private var observableViewModel: ObservableViewModelAdapter<VMDButtonViewModel<Content>>
    
    private var viewModel: VMDButtonViewModel<Content> {
        observableViewModel.viewModel
    }
    
    public init(_ viewModel: VMDButtonViewModel<Content>) {
        self.observableViewModel = viewModel.asObservable()
    }
    
    public var body: some View {
        VMDButton(viewModel) { (content: VMDImageContent) in
            content.image.image?.accessibilityLabel(content.contentDescription ?? "")
        }
    }
}
