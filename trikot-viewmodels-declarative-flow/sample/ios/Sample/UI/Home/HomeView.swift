import SwiftUI
import Trikot
import Jasper

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
            VMDList(viewModel.sections) { section in
                Section(header: VMDText(section.title)) {
                    ForEach(section.elements) { element in
                        VMDButton(element.button) { textContent in
                            Text(textContent.text)
                        }
                        .foregroundColor(.primary)
                    }
                }
            }
            .listStyle(GroupedListStyle())
            .navigationTitle(viewModel.title)
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
