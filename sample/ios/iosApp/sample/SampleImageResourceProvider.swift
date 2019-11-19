import Foundation
import MetaviewsSample
import Trikot_metaviews

class SampleImageResourceProvider: MetaImageResourceProvider {
    func image(fromResource resource: ImageResource?) -> UIImage? {
        if (resource as? ImageResources) == ImageResources.icon {
            return #imageLiteral(resourceName: "icon")
        }
        return nil
    }
}
