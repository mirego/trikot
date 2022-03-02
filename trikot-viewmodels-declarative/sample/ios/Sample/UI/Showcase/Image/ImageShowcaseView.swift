import SwiftUI
import Trikot
import TRIKOT_FRAMEWORK_NAME

struct ImageShowcaseView: RootViewModelView {
    typealias VM = ImageShowcaseViewModel

    @ObservedObject var observableViewModel: ObservableViewModelAdapter<ImageShowcaseViewModel>

    var viewModel: ImageShowcaseViewModel {
        return observableViewModel.viewModel
    }

    private let imageAspectRatio: CGFloat = 1.5

    init(viewModel: ImageShowcaseViewModel) {
        self.observableViewModel = viewModel.asObservable()
    }

    var body: some View {
        NavigationView {
            ScrollView {
                VStack(alignment: .leading, spacing: 40) {
                    ComponentShowcaseSectionView(viewModel.localImageTitle) {
                        VMDImage(viewModel.localImage)
                            .resizable()
                            .aspectRatio(imageAspectRatio, contentMode: .fill)
                    }

                    ComponentShowcaseSectionView(viewModel.remoteImageTitle) {
                        VMDImage(viewModel.remoteImage)
                            .resizable()
                            .aspectRatio(imageAspectRatio, contentMode: .fill)
                    }

                    ComponentShowcaseSectionView(viewModel.localImageDescriptorTitle) {
                        VMDImage(viewModel.localImageDescriptor)
                            .resizable()
                            .aspectRatio(imageAspectRatio, contentMode: .fill)
                    }

                    ComponentShowcaseSectionView(viewModel.remoteImageDescriptorTitle) {
                        VMDImage(viewModel.remoteImageDescriptor)
                            .resizable()
                            .aspectRatio(imageAspectRatio, contentMode: .fill)
                    }

                    ComponentShowcaseSectionView(viewModel.placeholderImageTitle) {
                        VMDImage(viewModel.placeholderImage)
                            .resizable()
                            .placeholder({ placeholderImage in
                                if let placeholderImage = placeholderImage {
                                    placeholderImage
                                        .resizable()
                                        .aspectRatio(imageAspectRatio, contentMode: .fill)
                                }
                            })
                            .aspectRatio(contentMode: .fit)
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

struct ImageShowcaseViewPreviews: PreviewProvider {
    static var previews: some View {
        return PreviewView {
            ImageShowcaseView(viewModel: ImageShowcaseViewModelPreview())
        }
    }
}
