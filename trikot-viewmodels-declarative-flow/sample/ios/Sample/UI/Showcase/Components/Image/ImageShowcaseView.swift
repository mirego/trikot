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
                LazyVStack(alignment: .leading, spacing: 40) {
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

                    ComponentShowcaseSectionView(viewModel.placeholderNoImageTitle) {
                        VMDImage(viewModel.placeholderNoImage)
                            .resizable()
                            .placeholder { progress, placeholderImage in
                                ImageShowcasePlaceHolder(
                                    progress: progress,
                                    placeholderImage: placeholderImage,
                                    imageAspectRatio: imageAspectRatio
                                )
                            }
                    }

                    ComponentShowcaseSectionView(viewModel.placeholderInvalidImageTitle) {
                        VMDImage(viewModel.placeholderInvalidImage)
                            .resizable()
                            .placeholder { progress, placeholderImage in
                                ImageShowcasePlaceHolder(
                                    progress: progress,
                                    placeholderImage: placeholderImage,
                                    imageAspectRatio: imageAspectRatio
                                )
                            }
                    }

                    ComponentShowcaseSectionView(viewModel.placeholderLoadingImageTitle) {
                        VMDImage(viewModel.placeholderLoadingImage)
                            .resizable()
                            .placeholder { progress, placeholderImage in
                                ImageShowcasePlaceHolder(
                                    progress: progress,
                                    placeholderImage: placeholderImage,
                                    imageAspectRatio: imageAspectRatio
                                )
                            }
                            .aspectRatio(imageAspectRatio, contentMode: .fill)
                            .clipped()
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
}

struct ImageShowcaseViewPreviews: PreviewProvider {
    static var previews: some View {
        return PreviewView {
            ImageShowcaseView(viewModel: ImageShowcaseViewModelPreview())
        }
    }
}

struct ImageShowcasePlaceHolder: View {
    let progress: Progress
    let placeholderImage: Image?
    let imageAspectRatio: CGFloat

    @Environment(\.vmdImageLoadingStatus) private var loadingStatus: VMDImageLoadingStatus

    var body: some View {
        Color.gray.opacity(0.25)
            .frame(maxWidth: .infinity)
            .aspectRatio(imageAspectRatio, contentMode: .fill)
            .overlay(
                    VStack(spacing: 10) {
                    if let placeholderImage {
                        placeholderImage
                            .resizable()
                            .aspectRatio(imageAspectRatio, contentMode: .fill)
                            .frame(width: 50 * imageAspectRatio, height: 50)
                    }

                    switch loadingStatus {
                    case .loading:
                        Text("Loading \(progress.fractionCompleted)")
                            .style(.subheadline)
                    case .error:
                        Text("Unable to load the remote image")
                            .style(.subheadline)
                    case .success:
                        EmptyView()
                    }
                }
            )
    }
}
