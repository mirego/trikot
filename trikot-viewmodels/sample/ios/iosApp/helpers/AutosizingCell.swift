import UIKit

class AutosizingCell<T: UIView>: UITableViewCell {
    var view = T()

    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)

        configureView()
    }

    @available(*, unavailable)
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func resetCellContent() {
        view = T()
        configureView()
    }

    private func configureView() {
        view.translatesAutoresizingMaskIntoConstraints = false
        contentView.subviews.forEach { $0.removeFromSuperview() }
        contentView.addSubview(view)

        selectionStyle = .none
        contentView.clipsToBounds = true

        NSLayoutConstraint.activate([
            contentView.topAnchor.constraint(equalTo: view.topAnchor),
            contentView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            contentView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            contentView.bottomAnchor.constraint(equalTo: view.bottomAnchor)])
    }
}
