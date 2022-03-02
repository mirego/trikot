import SwiftUI
import TRIKOT_FRAMEWORK_NAME

extension VMDImageResource {
    public var image: Image? {
        return Image(self)
    }
}

extension Image {
    public init?(_ imageResource: VMDImageResource) {
        if let uiImage = TrikotViewModelDeclarative.shared.imageProvider.imageForResource(imageResource: imageResource) {
            self.init(uiImage: uiImage)
        } else {
            return nil
        }
    }
}
