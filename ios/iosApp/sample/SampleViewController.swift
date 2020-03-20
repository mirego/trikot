import UIKit

class SampleViewController: UIViewController {
    private let sampleView = SampleView(frame: .zero)

    override func loadView() {
        sampleView.sampleViewModel = Core.shared.viewModelFactory.sampleViewModel
        view = sampleView
    }
}
