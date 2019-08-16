import UIKit

class SampleViewController: UIViewController {
    private let sampleView = SampleView(frame: .zero)

    override func loadView() {
        sampleView.viewModel = Core.shared.viewModelFactory.sampleViewModel
        view = sampleView
    }
}
