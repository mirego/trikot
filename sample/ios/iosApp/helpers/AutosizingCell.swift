import UIKit

protocol AutosizingCellViewGet {
    func getView() -> UIView
}

class AutosizingCell<T: UIView>: UITableViewCell, AutosizingCellViewGet {
    let view = T()

    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        view.translatesAutoresizingMaskIntoConstraints = false
        contentView.addSubview(view)

        contentView.clipsToBounds = true

        NSLayoutConstraint.activate([
            contentView.topAnchor.constraint(equalTo: view.topAnchor),
            contentView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            contentView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            contentView.bottomAnchor.constraint(equalTo: view.bottomAnchor)])
    }

    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func getView() -> UIView {
        return view
    }
}
