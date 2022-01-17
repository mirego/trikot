import TRIKOT_FRAMEWORK_NAME
import UIKit

extension VMDImageResource {
    public var uiImage: UIImage? {
        if let imageName = TrikotViewModelDeclarative.shared.imageProvider.imageNameForResource(imageResource: self) {
            return UIImage(named: imageName)
        } else {
            return nil
        }
    }
}
