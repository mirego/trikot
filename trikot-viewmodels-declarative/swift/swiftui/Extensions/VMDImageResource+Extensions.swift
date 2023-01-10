import SwiftUI
import TRIKOT_FRAMEWORK_NAME

extension VMDImageResource {
    public var image: Image? {
        return Image(self)
    }
}

extension Image {
    public init?(_ imageResource: VMDImageResource) {
        if let image = TrikotViewModelDeclarative.shared.imageProvider.imageForResource(imageResource: imageResource) {
            self = image
        } else {
            return nil
        }
    }
}
