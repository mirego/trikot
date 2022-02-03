import UIKit
import TRIKOT_FRAMEWORK_NAME

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

            bind(vm.elements, \ListView.items)
        }
    }

    override init(frame: CGRect) {
        super.init(frame: frame)

        backgroundColor = .black
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
        tableView.register(AutosizingCell<ToggleSwitchListItem>.self, forCellReuseIdentifier: AutosizingCell<ToggleSwitchListItem>.self.defaultReuseIdentifier)
        tableView.register(AutosizingCell<PickerListItem>.self, forCellReuseIdentifier: AutosizingCell<PickerListItem>.self.defaultReuseIdentifier)

        NSLayoutConstraint.activate([
            tableView.topAnchor.constraint(equalTo: topAnchor),
            tableView.bottomAnchor.constraint(equalTo: bottomAnchor),
            tableView.leadingAnchor.constraint(equalTo: leadingAnchor),
            tableView.trailingAnchor.constraint(equalTo: trailingAnchor)
        ])
    }

    @available(*, unavailable)
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

extension ListView: UITableViewDataSource {

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        items?.count ?? 0
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {

        switch items?[indexPath.row] {
        case let viewModel as NavigableListItemViewModel:
            let cell = prepareCell(type: NavigableListItem.self, tableView: tableView, indexPath: indexPath)
            cell.view.item = viewModel
            return cell

        case let viewModel as LabelListItemViewModel:
            let cell = prepareCell(type: LabelListItem.self, tableView: tableView, indexPath: indexPath)
            cell.view.item = viewModel
            return cell

        case let viewModel as HeaderListItemViewModel:
            let cell = prepareCell(type: HeaderListItem.self, tableView: tableView, indexPath: indexPath)
            cell.view.item = viewModel
            return cell

        case let viewModel as ViewListItemViewModel:
            let cell = prepareCell(type: ViewListItem.self, tableView: tableView, indexPath: indexPath)
            cell.view.item = viewModel
            return cell

        case let viewModel as ButtonListItemViewModel:
            let cell = prepareCell(type: ButtonListItem.self, tableView: tableView, indexPath: indexPath)
            cell.view.item = viewModel
            return cell

        case let viewModel as ImageListItemViewModel:
            let cell = prepareCell(type: ImageListItem.self, tableView: tableView, indexPath: indexPath)
            cell.view.item = viewModel
            return cell

        case let viewModel as InputTextListItemViewModel:
            let cell = prepareCell(type: InputTextListItem.self, tableView: tableView, indexPath: indexPath)
            cell.view.item = viewModel
            return cell

        case let viewModel as SliderListItemViewModel:
            let cell = prepareCell(type: SliderListItem.self, tableView: tableView, indexPath: indexPath)
            cell.view.item = viewModel
            return cell

        case let viewModel as ToggleSwitchListItemViewModel:
            let cell = prepareCell(type: ToggleSwitchListItem.self, tableView: tableView, indexPath: indexPath)
            cell.view.item = viewModel
            return cell

        case let viewModel as PickerListItemViewModel:
            let cell = prepareCell(type: PickerListItem.self, tableView: tableView, indexPath: indexPath)
            cell.view.item = viewModel
            return cell

        default:
            fatalError("Unexpected list item viewModel")
        }
    }

    private func prepareCell<T: UIView>(type: T.Type, tableView: UITableView, indexPath: IndexPath) -> AutosizingCell<T> {
        let cell = tableView.dequeueReusableCell(withCellType: AutosizingCell<T>.self, for: indexPath)
        cell.resetCellContent()
        return cell
    }
}

extension ListView: UITableViewDelegate {
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        self.endEditing(true)
    }
}
