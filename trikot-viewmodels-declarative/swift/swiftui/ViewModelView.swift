import SwiftUI
import SampleTrikotFrameworkName

public protocol ViewModelView: View {
    associatedtype VM: VMDViewModel

    var viewModel: VM { get }
}

public protocol RootViewModelView: ViewModelView {
    init(viewModel: VM)
}
