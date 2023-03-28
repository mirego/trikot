import SwiftUI
import TRIKOT_FRAMEWORK_NAME
import Trikot

class SampleSpanStyleProvider: VMDSpanStyleProvider {
    func spanStyleForResource(textStyleResource: VMDTextStyleResource) -> VMDSpanStyle? {
        guard let resource = textStyleResource as? SampleTextStyleResource else { return nil }
        switch resource {
        case .highlighted:
            return [NSAttributedString.Key.backgroundColor: UIColor.systemYellow]
        default:
            return nil
        }
    }
}
