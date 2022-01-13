import UIKit
import TRIKOT_FRAMEWORK_NAME

class SliderListItem: UIView {
    private let slider = UISlider()
    private let label = UILabel(frame: .zero)

    var item: SliderListItemViewModel? {
        didSet {
            viewModel = item
            slider.sliderViewModel = item?.slider
            label.labelViewModel = item?.valueLabel
        }
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        addSubview(slider)
        addSubview(label)

        slider.translatesAutoresizingMaskIntoConstraints = false
        label.translatesAutoresizingMaskIntoConstraints = false

        NSLayoutConstraint.activate([
            slider.topAnchor.constraint(equalTo: topAnchor, constant: 20),
            slider.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 20),
            slider.trailingAnchor.constraint(equalTo: trailingAnchor, constant: 20),

            label.topAnchor.constraint(equalTo: slider.bottomAnchor, constant: 20),
            label.bottomAnchor.constraint(equalTo: bottomAnchor, constant: 20),
            label.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 20),
            label.trailingAnchor.constraint(equalTo: trailingAnchor, constant: 20)
        ])
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
