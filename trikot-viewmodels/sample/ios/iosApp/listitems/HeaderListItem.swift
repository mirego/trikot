import UIKit
import TRIKOT_FRAMEWORK_NAME

class HeaderListItem: UIView {
    private let title = UILabel(frame: .zero)

    var item: HeaderListItemViewModel? {
        didSet {
            viewModel = item
            title.labelViewModel = item?.text
        }
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        addSubview(title)
        title.translatesAutoresizingMaskIntoConstraints = false
        title.font = UIFont.systemFont(ofSize: 15, weight: .semibold)
        title.textColor = #colorLiteral(red: 0.2549019754, green: 0.2745098174, blue: 0.3019607961, alpha: 1)
        NSLayoutConstraint.activate([
            title.topAnchor.constraint(equalTo: topAnchor, constant: 20),
            title.bottomAnchor.constraint(equalTo: bottomAnchor, constant: -20),
            title.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 8),
            title.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -8)
        ])
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
