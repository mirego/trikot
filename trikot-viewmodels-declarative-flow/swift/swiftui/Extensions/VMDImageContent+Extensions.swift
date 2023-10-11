import SwiftUI
import TRIKOT_FRAMEWORK_NAME

extension VMDImageContent {
    public var view: some Image {
        if let contentDescription = self.contentDescription {
            return Image(self.image)?.accessibilityLabel(contentDescription)
        } else {
            return Image(self.image)
        }
    }
}
