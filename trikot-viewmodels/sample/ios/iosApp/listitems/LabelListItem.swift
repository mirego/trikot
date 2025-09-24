import UIKit
import Jasper

class LabelListItem: UIView {
    private let title = UILabel(frame: .zero)

    var item: LabelListItemViewModel? {
        didSet {
            trikotViewModel = item
            title.trikotLabelViewModel = item?.label
        }
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        addSubview(title)
        title.translatesAutoresizingMaskIntoConstraints = false
        title.isUserInteractionEnabled = true
        title.numberOfLines = 0
        NSLayoutConstraint.activate([
            title.topAnchor.constraint(equalTo: topAnchor),
            title.bottomAnchor.constraint(equalTo: bottomAnchor),
            title.leadingAnchor.constraint(equalTo: leadingAnchor),
            title.trailingAnchor.constraint(equalTo: trailingAnchor)
        ])
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
