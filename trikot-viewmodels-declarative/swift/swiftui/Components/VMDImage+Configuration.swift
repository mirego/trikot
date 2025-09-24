import SwiftUI
import Jasper

public extension VMDImage {
    func resizable() -> VMDImage {
        configure {
            $0.resizable()
        } remote: { kfImage, _ in
            kfImage.resizable()
        }
    }

    func renderingMode(_ renderingMode: Image.TemplateRenderingMode) -> VMDImage {
        configure {
            $0.renderingMode(renderingMode)
        } remote: { kfImage, _ in
            kfImage.renderingMode(renderingMode)
        }
    }

    func placeholder<Content: View>(@ViewBuilder _ content: @escaping (_ placeholderImage: Image?) -> Content) -> VMDImage {
        configure {
            $0
        } remote: { kfImage, placehoder in
            kfImage.placeholder { progress in
                content(placehoder.image)
            }
        }
    }

    @available(*, deprecated, message: "Use placeholder(status:progress:placeholderImage) instead")
    func placeholder<Content: View>(@ViewBuilder _ content: @escaping (_ progress: Progress, _ placeholderImage: Image?) -> Content) -> VMDImage {
        configure {
            $0
        } remote: { kfImage, placehoder in
            kfImage.placeholder { progress in
                content(progress, placehoder.image)
            }
        }
    }

    func placeholder<Content: View>(@ViewBuilder _ content: @escaping (_ status: VMDImageLoadingStatus, _ progress: Progress, _ placeholderImage: Image?) -> Content) -> VMDImage {
        configure {
            $0
        } remote: { kfImage, placehoder in
            kfImage.placeholder { progress in
                content(loadingStatus, progress, placehoder.image)
            }
            .onSuccess { result in
                loadingStatus = .success
            }
            .onFailure { error in
                loadingStatus = .error
            }
        }
    }
}
