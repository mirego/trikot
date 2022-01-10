import UIKit

extension UIImage {
    public func imageWithTintColor(_ color: UIColor) -> UIImage? {
        let sourceImage = withRenderingMode(.alwaysTemplate)

        UIGraphicsBeginImageContextWithOptions(size, false, sourceImage.scale)
        color.set()
        sourceImage.draw(in: CGRect(x: 0, y: 0, width: size.width, height: size.height))
        let tintedImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()

        return tintedImage
    }
}
