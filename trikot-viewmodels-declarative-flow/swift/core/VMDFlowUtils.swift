import Foundation
import TRIKOT_FRAMEWORK_NAME

public class ObservableFlowWrapper<T: AnyObject>: ObservableObject {
    @Published public var value: T
    
    private var watcher: Closeable?
    
    init(_ flow: VMDFlow<T>, initial value: T) {
        self.value = value

        flow.watch { [weak self] t, closeable in
            self?.value = t
            self?.watcher = closeable
        }
    }
    
    deinit {
        watcher?.close()
    }
}
