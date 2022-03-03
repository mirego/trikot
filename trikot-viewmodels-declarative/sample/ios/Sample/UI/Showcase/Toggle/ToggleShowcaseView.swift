import SwiftUI
import Trikot
import TRIKOT_FRAMEWORK_NAME

struct ToggleShowcaseView: RootViewModelView {
    typealias VM = ToggleShowcaseViewModel

    @ObservedObject var observableViewModel: ObservableViewModelAdapter<ToggleShowcaseViewModel>

    var viewModel: ToggleShowcaseViewModel {
        return observableViewModel.viewModel
    }

    init(viewModel: ToggleShowcaseViewModel) {
        self.observableViewModel = viewModel.asObservable()
    }

    var body: some View {
        NavigationView {
            ScrollView {
                VStack(alignment: .leading, spacing: 40) {
                    Group {
                        ComponentShowcaseSectionView(viewModel.checkboxTitle) {
                            VMDToggle(viewModel.emptyToggle)
                                .toggleStyle(.squareCheckbox)
                        }

                        ComponentShowcaseSectionView(viewModel.textCheckboxTitle) {
                            VMDToggle(viewModel.textToggle)
                                .toggleStyle(.squareCheckboxWithLabel)
                                .textStyle(.body)
                        }

                        ComponentShowcaseSectionView(viewModel.imageCheckboxTitle) {
                            VMDToggle(viewModel.imageToggle) { imageContent in
                                Image(imageContent.image)
                            }
                            .toggleStyle(.squareCheckboxWithLabel)
                        }

                        ComponentShowcaseSectionView(viewModel.textImageCheckboxTitle) {
                            VMDToggle(viewModel.textImageToggle) { textImageContent in
                                HStack {
                                    Image(textImageContent.image)
                                    Text(textImageContent.text)
                                        .textStyle(.body)
                                }
                            }
                            .toggleStyle(.squareCheckboxWithLabel)
                        }

                        ComponentShowcaseSectionView(viewModel.textPairCheckboxTitle) {
                            VMDToggle(viewModel.textPairToggle) { textPairContent in
                                VStack(alignment: .leading) {
                                    Text(textPairContent.first)
                                        .textStyle(.body)
                                    Text(textPairContent.second)
                                        .textStyle(.caption1)
                                }
                            }
                            .toggleStyle(.squareCheckboxWithLabel)
                        }
                    }

                    Group {
                        ComponentShowcaseSectionView(viewModel.switchTitle) {
                            VMDToggle(viewModel.emptyToggle)
                                .toggleStyle(.switch)
                        }

                        ComponentShowcaseSectionView(viewModel.textSwitchTitle) {
                            VMDToggle(viewModel.textToggle)
                                .toggleStyle(.switch)
                                .textStyle(.body)
                        }

                        ComponentShowcaseSectionView(viewModel.imageSwitchTitle) {
                            VMDToggle(viewModel.imageToggle) { imageContent in
                                Image(imageContent.image)
                            }
                            .toggleStyle(.switch)
                        }

                        ComponentShowcaseSectionView(viewModel.textImageSwitchTitle) {
                            VMDToggle(viewModel.textImageToggle) { textImageContent in
                                HStack {
                                    Image(textImageContent.image)
                                    Text(textImageContent.text)
                                        .textStyle(.body)
                                }
                            }
                            .toggleStyle(.switch)
                        }

                        ComponentShowcaseSectionView(viewModel.textPairSwitchTitle) {
                            VMDToggle(viewModel.textPairToggle) { textPairContent in
                                VStack(alignment: .leading) {
                                    Text(textPairContent.first)
                                        .textStyle(.body)
                                    Text(textPairContent.second)
                                        .textStyle(.caption1)
                                }
                            }
                            .toggleStyle(.switch)
                        }
                    }
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding()
            }
            .navigationTitle(viewModel.title.text)
            .toolbar {
                VMDButton(viewModel.closeButton) { (content: VMDImageContent) in
                    content.image.image
                }
            }
        }
    }

    @ViewBuilder
    private func testPlaceholder(placeholderImage: Image?) -> some View {
        if let placeholderImage = placeholderImage {
            placeholderImage
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 50)
        }
    }
}

struct ToggleShowcaseViewPreviews: PreviewProvider {
    static var previews: some View {
        return PreviewView {
            ToggleShowcaseView(viewModel: ToggleShowcaseViewModelPreview())
        }
    }
}
