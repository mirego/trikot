import Jasper

class VMDCloseableExecuter {

    private var closeable: Closeable?

    init(closeable: Closeable) {
        self.closeable = closeable
    }

    deinit {
        closeable?.close()
    }
}
