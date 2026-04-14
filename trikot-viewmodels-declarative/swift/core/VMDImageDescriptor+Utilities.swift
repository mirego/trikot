import Foundation
import SampleTrikotFrameworkName

extension VMDImageDescriptor.Remote {
    var imageURL: URL? {
        if let imageURLString = url, let imageURL = URL(string: imageURLString) {
            return imageURL
        } else {
            return nil
        }
    }
}
