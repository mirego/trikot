import TRIKOT_FRAMEWORK_NAME
import UIKit

extension ImageResource {
    public var uiImage: UIImage? {
        ImageViewModelResourceManager.shared.image(fromResource: self)
    }
}
