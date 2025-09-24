import SwiftUI
import Jasper
import Trikot

class SampleImageProvider: VMDImageProvider {
    func imageForResource(imageResource: VMDImageResource) -> Image? {
        guard let resource = imageResource as? SampleImageResource else { return nil }
        switch resource {
        case .iconClose:
            return Image("icn_close")
        case .imageBridge:
            return Image("bridge")
        case .imagePlaceholder:
            return Image("placeholder")
        default:
            return nil
        }
    }
}
