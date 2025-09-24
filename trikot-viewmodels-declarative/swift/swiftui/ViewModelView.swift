import SwiftUI
import Jasper

public protocol ViewModelView: View {
    associatedtype VM: VMDViewModel

    var viewModel: VM { get }
}

public protocol RootViewModelView: ViewModelView {
    init(viewModel: VM)
}
