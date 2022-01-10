import UIKit
import ViewModelsSample

class PickerListItem: UIView {
    private let pickerItem = UIPickerView()

    var item: PickerListItemViewModel? {
        didSet {
            viewModel = item
            pickerItem.pickerViewModel = item?.picker
        }
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        addSubview(pickerItem)

        pickerItem.translatesAutoresizingMaskIntoConstraints = false

        NSLayoutConstraint.activate([
            pickerItem.topAnchor.constraint(equalTo: topAnchor, constant: 0),
            pickerItem.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 40),
            pickerItem.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -40),
            pickerItem.bottomAnchor.constraint(equalTo: bottomAnchor, constant: 0)
        ])
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
