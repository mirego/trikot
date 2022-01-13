import UIKit
import TRIKOT_FRAMEWORK_NAME

class InputTextListItem: UIView {
    private let textField = UITextField(frame: .zero)

    var item: InputTextListItemViewModel? {
        didSet {
            viewModel = item
            textField.inputTextViewModel = item?.inputText
        }
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        addSubview(textField)
        textField.translatesAutoresizingMaskIntoConstraints = false
        NSLayoutConstraint.activate([
            textField.topAnchor.constraint(equalTo: topAnchor),
            textField.bottomAnchor.constraint(equalTo: bottomAnchor),
            textField.leadingAnchor.constraint(equalTo: leadingAnchor),
            textField.trailingAnchor.constraint(equalTo: trailingAnchor),
            textField.heightAnchor.constraint(equalToConstant: 44)
        ])
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
