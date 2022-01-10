import Foundation
import ViewModelsSample
import Trikot_viewmodels

class SampleImageResourceProvider: ImageViewModelResourceProvider {
    func image(fromResource resource: ImageResource?) -> UIImage? {
        if (resource as? ImageResources) == ImageResources.icon {
            return #imageLiteral(resourceName: "icon")
        }
        return nil
    }
}
