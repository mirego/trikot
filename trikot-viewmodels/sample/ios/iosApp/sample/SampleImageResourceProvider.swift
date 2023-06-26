import Foundation
import TRIKOT_FRAMEWORK_NAME
import Trikot

class SampleImageResourceProvider: ImageViewModelResourceProvider {
    func image(fromResource resource: TrikotImageResource?) -> UIImage? {
        if (resource as? TrikotImageResources) == TrikotImageResources.icon {
            return #imageLiteral(resourceName: "icon")
        }
        return nil
    }
}
