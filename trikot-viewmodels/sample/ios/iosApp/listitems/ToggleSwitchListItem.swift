import UIKit
import Jasper

class ToggleSwitchListItem: UIView {
    private let switchItem = UISwitch()

    var item: ToggleSwitchListItemViewModel? {
        didSet {
            trikotViewModel = item
            switchItem.trikotToggleSwitchViewModel = item?.toggleSwitch
        }
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        addSubview(switchItem)

        switchItem.translatesAutoresizingMaskIntoConstraints = false

        NSLayoutConstraint.activate([
            switchItem.topAnchor.constraint(equalTo: topAnchor, constant: 0),
            switchItem.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 40),
            switchItem.trailingAnchor.constraint(equalTo: trailingAnchor, constant: 40),
            switchItem.bottomAnchor.constraint(equalTo: bottomAnchor, constant: 0)
        ])
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
