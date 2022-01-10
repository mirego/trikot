import UIKit

extension UITableViewCell {
    static var defaultReuseIdentifier: String {
        return String(describing: self)
    }
}

extension UITableView {
    func dequeueReusableCell<T>(withCellType: T.Type, for indexPath: IndexPath) -> T where T: UITableViewCell {
        return dequeueReusableCell(withIdentifier: T.defaultReuseIdentifier, for: indexPath) as! T
    }
}
