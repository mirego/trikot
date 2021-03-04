import UIKit
import SwiftUI
import TRIKOT_FRAMEWORK_NAME

protocol ViewModelView: View {
    associatedtype VM: ViewModel

    var viewModel: VM { get }
}

protocol RootViewModelView: ViewModelView {
    init(viewModel: VM)
}
