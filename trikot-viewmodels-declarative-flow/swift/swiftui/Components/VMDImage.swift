import SwiftUI
import Jasper
import Kingfisher

public struct VMDImage: View {
    public typealias LocalImageConfiguration = (Image) -> Image
    public typealias RemoteImageConfiguration = (KFImage, VMDImageResource) -> KFImage

    private var localImageConfigurations = [LocalImageConfiguration]()
    private var remoteImageConfigurations = [RemoteImageConfiguration]()

    private var viewModel: VMDImageViewModel {
        observableViewModel.viewModel
    }

    @ObservedObject private var observableViewModel: ObservableViewModelAdapter<VMDImageViewModel>
    @State var loadingStatus: VMDImageLoadingStatus = .loading

    public init(_ viewModel: VMDImageViewModel) {
        self.observableViewModel = viewModel.asObservable()
    }

    public init(_ imageDescriptor: VMDImageDescriptor) {
        self.observableViewModel = VMDComponents.Image.companion.withDescriptor(imageDescriptor: imageDescriptor, coroutineScope: CoroutineScopeProvider().provideMainWithSuperviserJob(), contentDescription: nil, closure: {_ in }).asObservable()
    }

    public var body: some View {
        if let localImage = viewModel.image as? VMDImageDescriptor.Local, let image = localImage.imageResource.image {
            localImageConfigurations.reduce(image, { current, config in
                config(current)
            })
            .accessibilityLabel(viewModel.contentDescription ?? "")
        } else if let remoteImage = viewModel.image as? VMDImageDescriptor.Remote {
            remoteImageConfigurations.reduce(
                KFImage(remoteImage.imageURL)
                    .onFailure { _ in
                        loadingStatus = .error
                    }
                    .onSuccess { _ in
                        loadingStatus = .success
                    }, { current, config in
                        config(current, remoteImage.placeholderImageResource)
                    }
            )
            .accessibilityLabel(viewModel.contentDescription ?? "")
            .environment(\.vmdImageLoadingStatus, loadingStatus)
        } else {
            EmptyView()
        }
    }

    public func configure(local configureLocalBlock: @escaping LocalImageConfiguration, remote configureRemoteBlock: @escaping RemoteImageConfiguration) -> VMDImage {
        if viewModel.image is VMDImageDescriptor.Local {
            return configureLocalImage(configureLocalBlock)
        } else if viewModel.image is VMDImageDescriptor.Remote {
            return configureRemoteImage(configureRemoteBlock)
        }
        return self
    }

    public func configureLocalImage(_ block: @escaping LocalImageConfiguration) -> VMDImage {
        var result = self
        result.localImageConfigurations.append(block)
        return result
    }

    public func configureRemoteImage(_ block: @escaping RemoteImageConfiguration) -> VMDImage {
        var result = self
        result.remoteImageConfigurations.append(block)
        return result
    }
}

public enum VMDImageLoadingStatus {
    case loading
    case success
    case error
}

public struct VMDImageLoadingStatusKey: EnvironmentKey {
    public static let defaultValue = VMDImageLoadingStatus.loading
}

public extension EnvironmentValues {
  var vmdImageLoadingStatus: VMDImageLoadingStatus {
    get { self[VMDImageLoadingStatusKey.self] }
    set { self[VMDImageLoadingStatusKey.self] = newValue }
  }
}
