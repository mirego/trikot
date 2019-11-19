import UIKit
import MetaviewsSample

class ButtonListItem: UIView {
    private let button = UIButton(frame: .zero)

    var item: MetaButtonListItem? {
        didSet {
            metaView = item
            button.metaButton = item?.button
        }
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        addSubview(button)
        button.translatesAutoresizingMaskIntoConstraints = false

        button.backgroundColor = #colorLiteral(red: 0.1764705882, green: 0.2196078431, blue: 0.6784313725, alpha: 1)
        button.setTitleColor(.black, for: .normal)
        button.setTitleColor(.red, for: .highlighted)
        button.setTitleColor(.blue, for: .disabled)
        button.setTitleColor(.white, for: .selected)
        button.titleLabel?.numberOfLines = 2
        button.titleLabel?.minimumScaleFactor = 0.4

        NSLayoutConstraint.activate([
            button.topAnchor.constraint(equalTo: topAnchor),
            button.bottomAnchor.constraint(equalTo: bottomAnchor),
            button.widthAnchor.constraint(equalToConstant: 200),
            button.heightAnchor.constraint(equalToConstant: 50),
            button.centerXAnchor.constraint(equalTo: centerXAnchor),
            widthAnchor.constraint(greaterThanOrEqualTo: button.widthAnchor)
        ])
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
