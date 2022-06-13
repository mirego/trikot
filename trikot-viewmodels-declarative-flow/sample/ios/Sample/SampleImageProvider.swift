import UIKit
import TRIKOT_FRAMEWORK_NAME

class SampleImageProvider: VMDImageProvider {
    func imageForResource(imageResource: VMDImageResource) -> UIImage? {
        guard let resource = imageResource as? SampleImageResource else { return nil }
        switch resource {
        case .iconClose:
            return UIImage(named: "icn_close")
        case .imageBridge:
            return UIImage(named: "bridge")
        case .imagePlaceholder:
            return UIImage(named: "placeholder")
        default:
            return nil
        }
    }
}
