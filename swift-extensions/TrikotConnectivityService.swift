import Foundation
import Reachability
import TRIKOT_FRAMEWORK_NAME

class TrikotConnectivityService {
    let reachability = Reachability()!
    let publisher = PublisherFactory().create(value: ConnectivityState.wifi)

    init() {
        reachability.whenReachable = {[weak self] reachability in
            if reachability.connection == .wifi {
                self?.publisher.value = ConnectivityState.wifi
            } else {
                self?.publisher.value = ConnectivityState.cellular
            }
        }
        reachability.whenUnreachable = {[weak self] _ in
            self?.publisher.value = ConnectivityState.none
        }
    }

    func start() {
        do {
            try reachability.startNotifier()
        } catch {
            print("Unable to start reachability notifier")
        }
    }

    func stop() {
        reachability.stopNotifier()
    }
}
