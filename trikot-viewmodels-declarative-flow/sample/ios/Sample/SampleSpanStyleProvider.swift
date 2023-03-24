import SwiftUI
import TRIKOT_FRAMEWORK_NAME
import Trikot

class SampleSpanStyleProvider: VMDSpanStyleProvider {
    func spanStyleForResource(spanStyleResource: VMDSpanStyleResource) -> VMDSpanStyle? {
        guard let resource = spanStyleResource as? SampleSpanStyleResource else { return nil }
        switch resource {
        case .highlighted:
            return [NSAttributedString.Key.backgroundColor: UIColor.systemYellow]
        default:
            return nil
        }
    }
}
