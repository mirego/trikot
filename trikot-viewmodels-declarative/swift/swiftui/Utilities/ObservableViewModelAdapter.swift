import SwiftUI
import Combine
import TRIKOT_FRAMEWORK_NAME

public class ObservableViewModelAdapter<VM: VMDViewModel>: ObservableObject {
    public let viewModel: VM
    private let propertyWillChangePublisher: AnyPublisher<KotlinUnit, Never>
    private var cancellable: AnyCancellable?

    public init(viewModel: VM, animation: Animation? = nil) {
        self.viewModel = viewModel
        self.propertyWillChangePublisher = viewModel.propertyWillChange.eraseToAnyPublisher()
        self.cancellable = propertyWillChangePublisher
            .receive(on: Foundation.DispatchQueue.main)
            .sink { [weak self] _ in
                if let animation = animation {
                    withAnimation(animation) {
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
    func asObservable<Self>(animation: Animation? = nil) -> ObservableViewModelAdapter<Self> {
        ObservableViewModelAdapter(viewModel: self as! Self, animation: animation)
    }
}
