import SwiftUI
import Trikot
import TRIKOT_FRAMEWORK_NAME

struct HomeView: RootViewModelView {
    typealias VM = HomeViewModel

    @ObservedObject var observableViewModel: ObservableViewModelAdapter<HomeViewModel>

    var viewModel: HomeViewModel {
        return observableViewModel.viewModel
    }

    init(viewModel: HomeViewModel) {
        self.observableViewModel = viewModel.asObservable()
    }

    var body: some View {
        NavigationView {
            VMDList(viewModel.items) { item in
                VMDButton(item) { textContent in
                    Text(textContent.text)
                }
                .foregroundColor(.black)
            }
            .listStyle(GroupedListStyle())
            .navigationTitle(viewModel.title.text)
        }
    }
}

struct HomeViewPreviews: PreviewProvider {
    static var previews: some View {
        return PreviewView {
            HomeView(viewModel: HomeViewModelPreview())
        }
    }
}
