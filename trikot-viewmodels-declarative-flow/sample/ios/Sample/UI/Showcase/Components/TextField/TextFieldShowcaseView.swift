import SwiftUI
import Trikot
import Jasper

struct TextFieldShowcaseView: RootViewModelView {
    typealias VM = TextFieldShowcaseViewModel

    @ObservedObject var observableViewModel: ObservableViewModelAdapter<TextFieldShowcaseViewModel>

    var viewModel: TextFieldShowcaseViewModel {
        return observableViewModel.viewModel
    }

    init(viewModel: TextFieldShowcaseViewModel) {
        self.observableViewModel = viewModel.asObservable()
    }

    var body: some View {
        NavigationView {
            ScrollView {
                VStack(alignment: .leading) {
                    HStack {
                        if #available(iOS 15, *) {
                            VMDTextField<EmptyView>(viewModel.textField) { placeholder in
                                Text(placeholder)
                                    .foregroundColor(.red)
                            }
                            .textFieldStyle(.roundedBorder)
                        } else {
                            VMDTextField<EmptyView>(viewModel.textField)
                                .textFieldStyle(.roundedBorder)
                        }

                        VMDButton(viewModel.clearButton) { textContent in
                            Text(textContent.text)
                        }
                    }

                    VMDText(viewModel.characterCountText)
                        .textStyle(.caption1)
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding()
            }
            .navigationTitle(viewModel.title.text)
            .toolbar {
                VMDImageButton(viewModel.closeButton)
            }
        }
    }
}

struct TextFieldShowcaseViewPreviews: PreviewProvider {
    static var previews: some View {
        return PreviewView {
            TextFieldShowcaseView(viewModel: TextFieldShowcaseViewModelPreview())
        }
    }
}
