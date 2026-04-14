import UIKit
import SampleTrikotFrameworkName

public protocol ImageViewModelResourceProvider {
    func image(fromResource resource: TrikotImageResource?) -> UIImage?
}

class DefaultImageViewModelResourceProvider: ImageViewModelResourceProvider {
    func image(fromResource resource: TrikotImageResource?) -> UIImage? {
        return nil
    }
}

public class ImageViewModelResourceManager {
    public static var shared: ImageViewModelResourceProvider = DefaultImageViewModelResourceProvider()
}
