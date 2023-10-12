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
                            .clipped()
                    }

                    ComponentShowcaseSectionView(viewModel.remoteImageTitle) {
                        VMDImage(viewModel.remoteImage)
                            .resizable()
                            .aspectRatio(imageAspectRatio, contentMode: .fill)
                            .clipped()
                    }

                    ComponentShowcaseSectionView(viewModel.localImageDescriptorTitle) {
                        VMDImage(viewModel.localImageDescriptor)
                            .resizable()
                            .aspectRatio(imageAspectRatio, contentMode: .fill)
                            .clipped()
                    }

                    ComponentShowcaseSectionView(viewModel.remoteImageDescriptorTitle) {
                        VMDImage(viewModel.remoteImageDescriptor)
                            .resizable()
                            .aspectRatio(imageAspectRatio, contentMode: .fill)
                            .clipped()
                    }

                    ComponentShowcaseSectionView(viewModel.placeholderImageTitle) {
                        VMDImage(viewModel.placeholderImage)
                            .resizable()
                            .placeholder({ placeholderImage in
                                if let placeholderImage = placeholderImage {
                                    placeholderImage
                                        .resizable()
                                        .aspectRatio(imageAspectRatio, contentMode: .fill)
                                        .clipped()
                                }
                            })
                    }

                    ComponentShowcaseSectionView(viewModel.complexPlaceholderImageTitle) {
                        VMDImage(viewModel.complexPlaceholderImage)
                            .resizable()
                            .placeholder({ status, progress, placeholderImage in
                                Color.gray.opacity(0.25)
                                    .frame(maxWidth: .infinity)
                                    .aspectRatio(imageAspectRatio, contentMode: .fill)
                                    .overlay(
                                            VStack(spacing: 10) {
                                            if let placeholderImage = placeholderImage {
                                                placeholderImage
                                                    .resizable()
                                                    .aspectRatio(imageAspectRatio, contentMode: .fill)
                                                    .frame(width: 50 * imageAspectRatio, height: 50)
                                            }

                                            switch status {
                                            case .empty:
                                                Text("There is no image to display")
                                                    .style(.subheadline)
                                            case .loading:
                                                Text("Loading \(progress.fractionCompleted)")
                                                    .style(.subheadline)
                                            case .error:
                                                Text("Unable to load the remote image")
                                                    .style(.subheadline)
                                            default:
                                                EmptyView()
                                            }
                                        }
                                    )
                            })
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

struct ImageShowcaseViewPreviews: PreviewProvider {
    static var previews: some View {
        return PreviewView {
            ImageShowcaseView(viewModel: ImageShowcaseViewModelPreview())
        }
    }
}
