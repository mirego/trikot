import UIKit
import ViewModelsSample

class ListView: UIView {
    private let tableView = UITableView()

    var items: [ListItemViewModel]? {
        didSet {
            tableView.reloadData()
        }
    }

    var vm: ListViewModel? {
        didSet {
            guard let vm = vm else { return }
            observe(vm.elements) {[weak self] (elements: [ListItemViewModel]) in
                self?.items = elements
            }
        }
    }

    override init(frame: CGRect) {
        super.init(frame: frame)

        backgroundColor = .white
        addSubview(tableView)
        tableView.dataSource = self
        tableView.delegate = self
        tableView.translatesAutoresizingMaskIntoConstraints = false

        tableView.separatorStyle = .none
        tableView.register(AutosizingCell<NavigableListItem>.self, forCellReuseIdentifier: AutosizingCell<NavigableListItem>.self.defaultReuseIdentifier)
        tableView.register(AutosizingCell<LabelListItem>.self, forCellReuseIdentifier: AutosizingCell<LabelListItem>.self.defaultReuseIdentifier)
        tableView.register(AutosizingCell<HeaderListItem>.self, forCellReuseIdentifier: AutosizingCell<HeaderListItem>.self.defaultReuseIdentifier)
        tableView.register(AutosizingCell<ViewListItem>.self, forCellReuseIdentifier: AutosizingCell<ViewListItem>.self.defaultReuseIdentifier)
        tableView.register(AutosizingCell<ButtonListItem>.self, forCellReuseIdentifier: AutosizingCell<ButtonListItem>.self.defaultReuseIdentifier)
        tableView.register(AutosizingCell<ImageListItem>.self, forCellReuseIdentifier: AutosizingCell<ImageListItem>.self.defaultReuseIdentifier)
        tableView.register(AutosizingCell<InputTextListItem>.self, forCellReuseIdentifier: AutosizingCell<InputTextListItem>.self.defaultReuseIdentifier)
        tableView.register(AutosizingCell<SliderListItem>.self, forCellReuseIdentifier: AutosizingCell<SliderListItem>.self.defaultReuseIdentifier)

        NSLayoutConstraint.activate([
            tableView.topAnchor.constraint(equalTo: topAnchor),
            tableView.bottomAnchor.constraint(equalTo: bottomAnchor),
            tableView.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 20),
            tableView.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -20)
        ])
    }

    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

extension ListView: UITableViewDataSource {

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return items?.count ?? 0
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let metaListItem = items?[indexPath.row]

        if let metaListItem = metaListItem as? NavigableListItemViewModel {
            let cell = tableView.dequeueReusableCell(withCellType: AutosizingCell<NavigableListItem>.self, for: indexPath)
            cell.view.item = metaListItem
            return cell
        } else if let metaListItem = metaListItem as? LabelListItemViewModel {
            let cell = tableView.dequeueReusableCell(withCellType: AutosizingCell<LabelListItem>.self, for: indexPath)
            cell.view.item = metaListItem
            return cell
        } else if let metaListItem = metaListItem as? HeaderListItemViewModel {
            let cell = tableView.dequeueReusableCell(withCellType: AutosizingCell<HeaderListItem>.self, for: indexPath)
            cell.view.item = metaListItem
            return cell
        } else if let metaListItem = metaListItem as? ViewListItemViewModel {
            let cell = tableView.dequeueReusableCell(withCellType: AutosizingCell<ViewListItem>.self, for: indexPath)
            cell.view.item = metaListItem
            return cell
        } else if let metaListItem = metaListItem as? ButtonListItemViewModel {
            let cell = tableView.dequeueReusableCell(withCellType: AutosizingCell<ButtonListItem>.self, for: indexPath)
            cell.view.item = metaListItem
            return cell
        } else if let metaListItem = metaListItem as? ImageListItemViewModel {
            let cell = tableView.dequeueReusableCell(withCellType: AutosizingCell<ImageListItem>.self, for: indexPath)
            cell.view.item = metaListItem
            return cell
        } else if let metaListItem = metaListItem as? InputTextListItemViewModel {
            let cell = tableView.dequeueReusableCell(withCellType: AutosizingCell<InputTextListItem>.self, for: indexPath)
            cell.view.item = metaListItem
            return cell
        } else if let metaListItem = metaListItem as? SliderListItemViewModel {
            let cell = tableView.dequeueReusableCell(withCellType: AutosizingCell<SliderListItem>.self, for: indexPath)
            cell.view.item = metaListItem
            return cell
        }

        return UITableViewCell(frame: .zero)
    }
}

extension ListView: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        //let metaListItem = items?[indexPath.row]
    }

    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        self.endEditing(true)
    }
}
