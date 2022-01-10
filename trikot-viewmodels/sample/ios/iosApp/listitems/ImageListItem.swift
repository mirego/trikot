import UIKit
import ViewModelsSample

class ImageListItem: UIView {
    private let image = UIImageView(frame: .zero)

    var item: ImageListItemViewModel? {
        didSet {
            viewModel = item
            image.imageViewModel = item?.image
        }
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        addSubview(image)
        image.translatesAutoresizingMaskIntoConstraints = false

        image.backgroundColor = .green
        image.contentMode = .scaleAspectFit
        image.isUserInteractionEnabled = true
        NSLayoutConstraint.activate([
            image.topAnchor.constraint(equalTo: topAnchor),
            image.bottomAnchor.constraint(equalTo: bottomAnchor),
            image.widthAnchor.constraint(equalToConstant: 200),
            image.heightAnchor.constraint(equalToConstant: 200),
            image.centerXAnchor.constraint(equalTo: centerXAnchor),
            widthAnchor.constraint(greaterThanOrEqualTo: image.widthAnchor)
        ])
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
