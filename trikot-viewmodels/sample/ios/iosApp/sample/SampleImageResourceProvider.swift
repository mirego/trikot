import Foundation
import TRIKOT_FRAMEWORK_NAME
import Trikot_viewmodels

class SampleImageResourceProvider: ImageViewModelResourceProvider {
    func image(fromResource resource: ImageResource?) -> UIImage? {
        if (resource as? ImageResources) == ImageResources.icon {
            return #imageLiteral(resourceName: "icon")
        }
        return nil
    }
}
