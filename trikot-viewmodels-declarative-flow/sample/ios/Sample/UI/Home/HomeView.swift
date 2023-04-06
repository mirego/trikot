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
        ZStack {
            VMDList(viewModel.sections) { section in
                Section(header: VMDText(section.title)) {
                    ForEach(section.elements) { element in
                        VMDButton(element.button) { textContent in
                            Text(textContent.text)
                        }
                        .foregroundColor(.black)
                    }
                }
            }
            .listStyle(GroupedListStyle())
            SnackbarView(flow: viewModel.snackbar)
                .padding(.bottom, 24)
        }
        .navigationTitle(viewModel.title)
        .navigationBarBackButtonHidden(true)
    }
}

struct HomeViewPreviews: PreviewProvider {
    static var previews: some View {
        return PreviewView {
            HomeView(viewModel: HomeViewModelPreview())
        }
    }
}
