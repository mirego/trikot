import Foundation
import TRIKOT_FRAMEWORK_NAME

public class ObservableFlowWrapper<T: AnyObject>: ObservableObject {
    @Published public var value: T
    
    private var watcher: Closeable__?
    
    init(_ flow: CFlow<T>, initial value: T) {
        self.value = value

        watcher = flow.watch { [weak self] t in
            self?.value = t
        }
    }
    
    deinit {
        watcher?.close()
    }
}
