import UIKit
import TrikotFrameworkName

class SampleView: UIView {
    private let label = UILabel()
    private let button = UIButton()

    var sampleViewModel: SampleViewModel? {
        didSet {
            label.labelViewModel = sampleViewModel?.quoteLabel
            button.buttonViewModel = sampleViewModel?.refreshButton
        }
    }

    override init(frame: CGRect) {
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
        NSLayoutConstraint.activate([
            label.leadingAnchor.constraint(greaterThanOrEqualTo: leadingAnchor),
            label.trailingAnchor.constraint(lessThanOrEqualTo: trailingAnchor),
            label.centerXAnchor.constraint(equalTo: centerXAnchor),
            label.centerYAnchor.constraint(equalTo: centerYAnchor),
            button.centerXAnchor.constraint(equalTo: centerXAnchor),
            button.bottomAnchor.constraint(equalTo: bottomAnchor, constant: -50)
            ])
    }

    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
