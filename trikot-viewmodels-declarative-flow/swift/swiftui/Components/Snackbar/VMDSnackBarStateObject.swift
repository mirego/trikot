import SwiftUI
import TRIKOT_FRAMEWORK_NAME

public class VMDSnackBarStateObject: ObservableObject {
    @Published public var message = ""
    @Published public var duration: TimeInterval = VMDSnackbarDuration.short_.toTimeInterval()
    @Published public var isVisible = false
    @Published public var withDismissAction = true

    private var closeableExecuter: VMDCloseableExecuter?

    public func startWatching(_ flow: VMDFlow<VMDSnackbarViewData>) {
        flow.watch { [weak self] viewData, closeable in
            self?.message = viewData?.message ?? ""
            self?.duration = viewData?.duration.toTimeInterval() ?? VMDSnackbarDuration.short_.toTimeInterval()
            self?.isVisible = true
            self?.withDismissAction = viewData?.withDismissAction ?? true

            if self?.closeableExecuter == nil {
                self?.closeableExecuter = VMDCloseableExecuter(closeable: closeable)
            }

            guard viewData?.duration != VMDSnackbarDuration.indefinite, let duration = self?.duration else { return }

            let delayTime = DispatchTime.now() + duration
            DispatchQueue.main.asyncAfter(deadline: delayTime) {
                self?.isVisible = false
            }
        }
    }
}

extension VMDSnackbarDuration {
    func toTimeInterval() -> TimeInterval {
        switch(self) {
        case .short_:
            return 5
        case .long_:
            return 10
        case .indefinite:
            return -1
        default:
            return -1
        }
    }
}
