import UIKit
import SwiftUI
import TRIKOT_FRAMEWORK_NAME

public protocol ViewModelView: View {
    associatedtype VM: ViewModel

    var viewModel: VM { get }
}

public protocol RootViewModelView: ViewModelView {
    init(viewModel: VM)
}
