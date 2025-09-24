import Jasper
import UIKit

extension VMDImageResource {
    public var uiImage: UIImage? {
        return TrikotViewModelDeclarativeUIKit.shared.imageProvider.imageForResource(imageResource: self)
    }
}
