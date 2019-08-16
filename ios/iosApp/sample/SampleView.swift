import UIKit
import TrikotFrameworkName

class SampleView: UIView {
    private let label = UILabel()

    var viewModel: SampleViewModel? {
        didSet {
            label.metaLabel = viewModel?.helloWorldLabel
        }
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        label.font = UIFont.boldSystemFont(ofSize: 30)
        label.textColor = .white
        label.translatesAutoresizingMaskIntoConstraints = false
        addSubview(label)
        NSLayoutConstraint.activate([
            label.topAnchor.constraint(equalTo: topAnchor, constant: 50),
            label.leadingAnchor.constraint(equalTo: leadingAnchor)
            ])
    }

    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
