import SwiftUI
import Combine
import Jasper

public class ObservableViewModelAdapter<VM: VMDViewModel>: ObservableObject {
    public let viewModel: VM
    private let propertyWillChangePublisher: AnyPublisher<VMDPropertyChange<AnyObject>, Never>
    private var cancellable: AnyCancellable?

    public init(viewModel: VM) {
        self.viewModel = viewModel
        self.propertyWillChangePublisher = viewModel.propertyWillChange.eraseToAnyPublisher()
        self.cancellable = propertyWillChangePublisher
            .receive(on: Foundation.DispatchQueue.main)
            .sink { [weak self] propertyChange in
                if let propertyChangeAnimation = propertyChange.animation {
                    withAnimation(propertyChangeAnimation.animation) {
                        self?.objectWillChange.send()
                    }
                } else {
                    self?.objectWillChange.send()
                }
            }
    }

    deinit {
        cancellable?.cancel()
    }
}

public extension VMDViewModel {
    func asObservable<Self>() -> ObservableViewModelAdapter<Self> {
        ObservableViewModelAdapter(viewModel: self as! Self)
    }
}
