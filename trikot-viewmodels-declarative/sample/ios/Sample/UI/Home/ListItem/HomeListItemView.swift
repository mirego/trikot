import SwiftUI
import Trikot_viewmodels_declarative
import TrikotViewmodelsDeclarativeSample

struct HomeListItemView: ViewModelView {
    typealias VM = HomeListItemViewModel

    @ObservedObject var observableViewModel: ObservableViewModelAdapter<HomeListItemViewModel>

    var viewModel: HomeListItemViewModel {
        return observableViewModel.viewModel
    }

    init(viewModel: HomeListItemViewModel) {
        self.observableViewModel = viewModel.asObservable()
    }

    var body: some View {
        VMDText(viewModel.name)
    }
}

struct HomeListItemViewPreviews: PreviewProvider {
    static var previews: some View {
        return PreviewView {
            HomeListItemView(viewModel: HomeListItemViewModelPreview(componentName: "Component 1"))
        }
    }
}
