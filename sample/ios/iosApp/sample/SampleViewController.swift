import UIKit
import MetaviewsSample

class ListViewController: UIViewController {
    private let sampleView = ListView(frame: .zero)
    private let listViewModel: ListViewModel

    init(listViewModel: ListViewModel) {
        self.listViewModel = listViewModel
        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func loadView() {
        sampleView.viewModel = listViewModel
        view = sampleView
    }
}
