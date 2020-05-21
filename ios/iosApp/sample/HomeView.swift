import TrikotFrameworkName
import UIKit

class HomeView: BaseViewModelView<HomeViewModel> {
    private let label = UILabel()
    private let button = UIButton()

    override var viewViewModel: HomeViewModel? {
        didSet {
            label.labelViewModel = viewViewModel?.quoteLabel
            button.buttonViewModel = viewViewModel?.refreshButton
        }
    }

    required init(frame: CGRect) {
        super.init(frame: frame)
        label.font = UIFont.boldSystemFont(ofSize: 30)
        label.textColor = .white
        label.textAlignment = .center
        label.numberOfLines = 0
        label.translatesAutoresizingMaskIntoConstraints = false
        addSubview(label)
        button.translatesAutoresizingMaskIntoConstraints = false
        addSubview(button)
        button.backgroundColor = .gray
        NSLayoutConstraint.activate(
            [
                label.leadingAnchor.constraint(greaterThanOrEqualTo: leadingAnchor),
                label.trailingAnchor.constraint(lessThanOrEqualTo: trailingAnchor),
                label.centerXAnchor.constraint(equalTo: centerXAnchor),
                label.centerYAnchor.constraint(equalTo: centerYAnchor),
                button.centerXAnchor.constraint(equalTo: centerXAnchor),
                button.bottomAnchor.constraint(equalTo: bottomAnchor, constant: -50)
            ]
        )
    }
}
