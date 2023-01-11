import TRIKOT_FRAMEWORK_NAME
import UIKit

extension VMDImageResource {
    public var uiImage: UIImage? {
        return TrikotViewModelDeclarativeUIKit.shared.imageProvider.imageForResource(imageResource: self)
    }
}
