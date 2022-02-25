import SwiftUI
import TRIKOT_FRAMEWORK_NAME

extension VMDImageResource {
    public var image: Image? {
        if let uiImage = TrikotViewModelDeclarative.shared.imageProvider.imageForResource(imageResource: self) {
            return Image(uiImage: uiImage)
        }
        return nil
    }
}
