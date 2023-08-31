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
            guard let self else { return }

            if self.closeableExecuter == nil {
                self.closeableExecuter = VMDCloseableExecuter(closeable: closeable)
            }

            guard let viewData else { return }

            self.message = viewData.message
            self.duration = viewData.duration.toTimeInterval()
            self.isVisible = true
            self.withDismissAction = viewData.withDismissAction

            guard viewData.duration != VMDSnackbarDuration.indefinite else { return }

            let delayTime = DispatchTime.now() + self.duration
            DispatchQueue.main.asyncAfter(deadline: delayTime) { [weak self] in
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
