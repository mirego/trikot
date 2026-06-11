import Foundation
import TRIKOT_FRAMEWORK_NAME

extension VMDImageDescriptor.Remote {
    var imageURL: URL? {
        if let imageURLString = url, let imageURL = URL(string: imageURLString) {
            return imageURL
        } else {
            return nil
        }
    }
}
