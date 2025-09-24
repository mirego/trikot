import SwiftUI
import Trikot
import Jasper

struct SnackbarShowcaseView: RootViewModelView {
    typealias VM = SnackbarShowcaseViewModel

    @ObservedObject var observableViewModel: ObservableViewModelAdapter<SnackbarShowcaseViewModel>

    var viewModel: SnackbarShowcaseViewModel {
        return observableViewModel.viewModel
    }

    init(viewModel: SnackbarShowcaseViewModel) {
        self.observableViewModel = viewModel.asObservable()
    }

    var body: some View {
        NavigationView {
            ZStack(alignment: .bottom) {
                ScrollView {
                    VStack(alignment: .leading, spacing: 40) {
                        ForEach(viewModel.buttons, id: \.self) { button in
                            VMDButton(button) { textContent in
                                Text(textContent.text)
                            }
                            .foregroundColor(.black)
                        }
                    }
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding()
                }
                SnackbarView(flow: viewModel.snackbar)
                    .padding(.bottom, 24)
            }
            .navigationTitle(viewModel.title.text)
            .toolbar {
                VMDButton(viewModel.closeButton) { (content: VMDImageContent) in
                    content.image.image
                }
            }
        }
    }
}
