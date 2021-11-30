import SwiftUI
import TRIKOT_FRAMEWORK_NAME
import Kingfisher

struct VMDImage: View {
    public typealias LocalImageConfiguration = (Image) -> Image
    public typealias RemoteImageConfiguration = (KFImage) -> KFImage

    private var localImageConfigurations = [LocalImageConfiguration]()
    private var remoteImageConfigurations = [RemoteImageConfiguration]()

    private var viewModel: VMDImageViewModel {
        observableViewModel.viewModel
    }

    @ObservedObject private var observableViewModel: ObservableViewModelAdapter<VMDImageViewModel>

    init(_ viewModel: VMDImageViewModel) {
        self.observableViewModel = viewModel.asObservable()
    }

    var body: some View {
        if let localImage = viewModel.image as? VMDImageDescriptor.Local, let image = localImage.imageResource.image {
            localImageConfigurations.reduce(image, { current, config in
                config(current)
            })
        } else if let remoteImage = viewModel.image as? VMDImageDescriptor.Remote, let url = URL(string: remoteImage.url) {
            remoteImageConfigurations.reduce(KFImage(url), { current, config in
                config(current)
            })
        } else {
            EmptyView()
        }
    }

    public func configure(local configureLocalBlock: @escaping LocalImageConfiguration, remote configureRemoteBlock: @escaping (KFImage) -> KFImage) -> VMDImage {
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
