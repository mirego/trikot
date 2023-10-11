import SwiftUI
import Trikot
import TRIKOT_FRAMEWORK_NAME

struct ButtonShowcaseView: RootViewModelView {
    typealias VM = ButtonShowcaseViewModel

    @ObservedObject var observableViewModel: ObservableViewModelAdapter<ButtonShowcaseViewModel>

    var viewModel: ButtonShowcaseViewModel {
        return observableViewModel.viewModel
    }

    init(viewModel: ButtonShowcaseViewModel) {
        self.observableViewModel = viewModel.asObservable()
    }

    var body: some View {
        NavigationView {
            ScrollView {
                VStack(alignment: .leading, spacing: 40) {
                    ComponentShowcaseSectionView(viewModel.textButtonTitle) {
                        VMDButton(viewModel.textButton) { textContent in
                            Text(textContent.text)
                        }
                        .buttonStyle(.roundedShadow)
                    }

                    ComponentShowcaseSectionView(viewModel.imageButtonTitle) {
                        VMDButton(viewModel.imageButton) { imageContent in
                            Image(imageContent.image)?.renderingMode(.template).accessibilityLabel(imageContent.contentDescription ?? "")
                        }
                        .buttonStyle(.roundedShadow)
                    }

                    ComponentShowcaseSectionView(viewModel.textImageButtonTitle) {
                        VMDButton(viewModel.textImageButton) { textImageContent in
                            HStack(alignment: .center, spacing: 10) {
                                Image(textImageContent.image)?.renderingMode(.template)
                                Text(textImageContent.text)
                            }
                        }
                        .buttonStyle(.roundedShadow)
                    }

                    ComponentShowcaseSectionView(viewModel.textPairButtonTitle) {
                        VMDButton(viewModel.textPairButton) { textPairContent in
                            VStack(alignment: .leading, spacing: 4) {
                                Text(textPairContent.first)
                                Text(textPairContent.second)
                                    .textStyle(.caption1)
                            }
                        }
                        .buttonStyle(.roundedShadow)
                    }
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

struct ButtonShowcaseViewPreviews: PreviewProvider {
    static var previews: some View {
        return PreviewView {
            ButtonShowcaseView(viewModel: ButtonShowcaseViewModelPreview())
        }
    }
}
