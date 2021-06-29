import TRIKOT_FRAMEWORK_NAME
import UIKit

public protocol ImageViewModelResourceProvider {
    func image(fromResource resource: ImageResource?) -> UIImage?
}

class DefaultImageViewModelResourceProvider: ImageViewModelResourceProvider {
    func image(fromResource resource: ImageResource?) -> UIImage? {
        return nil
    }
}

public enum ImageViewModelResourceManager {
    public static var shared: ImageViewModelResourceProvider = DefaultImageViewModelResourceProvider()
}
