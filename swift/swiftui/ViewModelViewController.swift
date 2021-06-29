import UIKit
import SwiftUI
import TRIKOT_FRAMEWORK_NAME

class ViewModelViewController<VMC: ViewModelController<N, VM>, VM, V: RootViewModelView, N: NavigationDelegate>: UIHostingController<V>, NavigationDelegate where VM == V.VM {

    let viewModelController: VMC
    var viewControllerFactory: ViewControllerFactory!

    init(viewModelController: VMC, createViewClosure: (VMC) -> V = createRootView) {
        self.viewModelController = viewModelController
        super.init(rootView: createViewClosure(viewModelController))
        viewModelController.navigationDelegate = self as? N
        viewModelController.onCreate()
    }

    private static func createRootView(viewModelController: VMC) -> V {
        V.init(viewModel: viewModelController.viewModel)
    }

    @available(*, unavailable)
    @objc required dynamic init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        viewModelController.onAppear()
        NotificationCenter.default.addObserver(self, selector: #selector(applicationWillEnterForeground), name: UIApplication.willEnterForegroundNotification, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(applicationDidEnterBackground), name: UIApplication.didEnterBackgroundNotification, object: nil)
    }

    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidAppear(animated)
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
