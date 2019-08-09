import UIKit
import TRIKOT_FRAMEWORK_NAME

public protocol MetaImageResourceProvider {
    func image(fromResource resource: ImageResource?) -> UIImage?
}

class DefaultMetaImageResourceProvider: MetaImageResourceProvider {
    func image(fromResource resource: ImageResource?) -> UIImage? {
        return nil
    }
}

public class MetaImageResourceManager {
    public static var shared: MetaImageResourceProvider = DefaultMetaImageResourceProvider()
}
