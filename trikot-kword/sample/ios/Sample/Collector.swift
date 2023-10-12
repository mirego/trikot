import TRIKOT_FRAMEWORK_NAME

class Collector<T>: FlowCollector {
    let callback:(T) -> Void

    init(callback: @escaping (T) -> Void) {
        self.callback = callback
    }
    
    func emit(value: Any?, completionHandler: @escaping (Error?) -> Void) {
        callback(value as! T)

        completionHandler(nil)
    }
}
