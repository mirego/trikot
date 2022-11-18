import TRIKOT_FRAMEWORK_NAME

class VMDCloseableExecuter {

    private var closeable: Closeable?

    init(closeable: Closeable) {
        self.closeable = closeable
    }

    deinit {
        closeable?.close()
    }
}
