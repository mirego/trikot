import TRIKOT_FRAMEWORK_NAME

class Collector<T>: Kotlinx_coroutines_coreFlowCollector {
    
    let callback:(T) -> Void

    init(callback: @escaping (T) -> Void) {
        self.callback = callback
    }


    func emit(value: Any?, completionHandler: @escaping (KotlinUnit?, Error?) -> Void) {
        callback(value as! T)

        completionHandler(KotlinUnit(), nil)
    }
}
