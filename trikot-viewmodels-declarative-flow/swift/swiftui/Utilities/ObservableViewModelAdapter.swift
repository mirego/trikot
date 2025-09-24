import SwiftUI
import Combine
import Jasper

public class ObservableViewModelAdapter<VM: VMDViewModel>: ObservableObject {
    public let viewModel: VM
    private var closeable: Closeable?

    public init(viewModel: VM) {
        self.viewModel = viewModel
        viewModel.propertyWillChange.watch { [weak self] propertyChange, closeable in
            self?.closeable = closeable
            if let propertyChangeAnimation = propertyChange?.animation {
                withAnimation(propertyChangeAnimation.animation) {
                    self?.objectWillChange.send()
                }
            } else {
                self?.objectWillChange.send()
            }
        }
    }

    deinit {
        closeable?.close()
    }
}

public extension VMDViewModel {
    func asObservable<Self>() -> ObservableViewModelAdapter<Self> {
        ObservableViewModelAdapter(viewModel: self as! Self)
    }
}
