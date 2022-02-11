import TRIKOT_FRAMEWORK_NAME
import UIKit

extension VMDImageResource {
    public var uiImage: UIImage? {
        return TrikotViewModelDeclarative.shared.imageProvider.imageNameForResource(imageResource: self)
    }
}
