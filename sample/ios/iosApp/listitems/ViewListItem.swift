import UIKit
import MetaviewsSample

class ViewListItem: UIView {
    private let view = UIView(frame: .zero)

    var item: MetaViewListItem? {
        didSet {
            metaView = item
            view.metaView = item?.view
        }
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        addSubview(view)
        view.translatesAutoresizingMaskIntoConstraints = false
        view.backgroundColor = .gray

        NSLayoutConstraint.activate([
            view.topAnchor.constraint(equalTo: topAnchor),
            view.bottomAnchor.constraint(equalTo: bottomAnchor),
            view.centerXAnchor.constraint(equalTo: centerXAnchor),
            view.widthAnchor.constraint(equalToConstant: 50),
            view.heightAnchor.constraint(equalToConstant: 50)
        ])
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
