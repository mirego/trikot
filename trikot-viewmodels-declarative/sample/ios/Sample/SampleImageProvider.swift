import UIKit
import TRIKOT_FRAMEWORK_NAME

class SampleImageProvider: VMDImageProvider {
    func imageNameForResource(imageResource: VMDImageResource) -> UIImage? {
        guard let resource = imageResource as? SampleImageResource else { return nil }
        switch resource {
        case .iconClose:
            return UIImage(named: "icn_close")
        default:
            return nil
        }
    }
}
