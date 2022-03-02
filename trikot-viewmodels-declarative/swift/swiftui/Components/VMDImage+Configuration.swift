import SwiftUI
import TRIKOT_FRAMEWORK_NAME

public extension VMDImage {
    public func resizable() -> VMDImage {
        configure {
            $0.resizable()
        } remote: { kfImage, _ in
            kfImage.resizable()
        }
    }

    public func renderingMode(_ renderingMode: Image.TemplateRenderingMode) -> VMDImage {
        configure {
            $0.renderingMode(renderingMode)
        } remote: { kfImage, _ in
            kfImage.renderingMode(renderingMode)
        }
    }

    public func placeholder<Content: View>(@ViewBuilder _ content: @escaping (_ placeholderImage: Image?) -> Content) -> VMDImage {
        configure {
            $0
        } remote: { kfImage, placehoder in
            kfImage.placeholder { progress in
                content(placehoder.image)
            }
        }
    }

    public func placeholder<Content: View>(@ViewBuilder _ content: @escaping (_ progress: Progress, _ placeholderImage: Image?) -> Content) -> VMDImage {
        configure {
            $0
        } remote: { kfImage, placehoder in
            kfImage.placeholder { progress in
                content(progress, placehoder.image)
            }
        }
    }
}
