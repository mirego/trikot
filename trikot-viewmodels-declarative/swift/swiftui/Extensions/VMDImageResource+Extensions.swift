import SwiftUI
import TRIKOT_FRAMEWORK_NAME

extension VMDImageResource {
    public var image: Image? {
        if let uiImage = TrikotViewModelDeclarative.shared.imageProvider.imageNameForResource(imageResource: self) {
            return Image(uiImage: uiImage)
        }
        return nil
    }
}
