import Foundation
import Reachability
import TRIKOT_FRAMEWORK_NAME

public class TrikotConnectivityService {
    public static let shared = TrikotConnectivityService()
    public let publisher = Publishers().publishSubject()

    let reachability = Reachability()!

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

    public func start() {
        do {
            try reachability.startNotifier()
        } catch {
            publisher.value = ConnectivityState.none
            print("Unable to start reachability notifier")
        }
    }

    public func stop() {
        reachability.stopNotifier()
    }
}
