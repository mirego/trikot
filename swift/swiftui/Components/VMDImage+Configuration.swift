import SwiftUI
import TRIKOT_FRAMEWORK_NAME

extension VMDImage {
    public func resizable() -> VMDImage {
        configure {
            $0.resizable()
        } remote: {
            $0.resizable()
        }
    }

    public func renderingMode(_ renderingMode: Image.TemplateRenderingMode) -> VMDImage {
        configure {
            $0.renderingMode(renderingMode)
        } remote: {
            $0.renderingMode(renderingMode)
        }
    }

    public func placeholder<Content: View>(@ViewBuilder _ content: @escaping () -> Content) -> VMDImage {
        configure {
            $0
        } remote: {
            $0.placeholder(content)
        }
    }

    public func placeholder<Content: View>(@ViewBuilder _ content: @escaping (_ progress: Progress) -> Content) -> VMDImage {
        configure {
            $0
        } remote: {
            $0.placeholder(content)
        }
    }
}
