import UIKit
import SwiftUI
import Jasper

open class ViewModelViewController<VMC: VMDViewModelController<VM, N>, VM, V: RootViewModelView, N: VMDNavigationDelegate>: UIHostingController<V>, VMDNavigationDelegate where VM == V.VM {

    public let viewModelController: VMC

    public init(viewModelController: VMC, createViewClosure: (VMC) -> V = createRootView) {
        self.viewModelController = viewModelController
        super.init(rootView: createViewClosure(viewModelController))
        viewModelController.navigationDelegate = self as? N
        viewModelController.onCreate()
    }

    public static func createRootView(viewModelController: VMC) -> V {
        V.init(viewModel: viewModelController.viewModel)
    }

    @available(*, unavailable)
    @objc required dynamic public init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    open override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        viewModelController.onAppear()
        NotificationCenter.default.addObserver(self, selector: #selector(applicationWillEnterForeground), name: UIApplication.willEnterForegroundNotification, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(applicationDidEnterBackground), name: UIApplication.didEnterBackgroundNotification, object: nil)
    }

    open override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        viewModelController.onDisappear()

        NotificationCenter.default.removeObserver(self, name: UIApplication.willEnterForegroundNotification, object: nil)
        NotificationCenter.default.removeObserver(self, name: UIApplication.didEnterBackgroundNotification, object: nil)
    }

    deinit {
        NotificationCenter.default.removeObserver(self)
        viewModelController.onCleared()
    }

    @objc func applicationWillEnterForeground() {
        viewModelController.onAppear()
    }

    @objc func applicationDidEnterBackground() {
        viewModelController.onDisappear()
    }
}
