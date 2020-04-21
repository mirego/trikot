import Foundation
import Mixpanel
import TRIKOT_FRAMEWORK_NAME

public class MixpanelAnalyticsService: AnalyticsService {
    public init(enableAnalytics: Bool = true) {
        enabled = enableAnalytics
    }

    public var enabled: Bool {
        didSet {
            if enabled {
                Mixpanel.mainInstance().optInTracking()
            }
            else {
                Mixpanel.mainInstance().optOutTracking()
            }
        }
    }

    public func identifyUser(userId: String, properties: [String: Any]) {
        Mixpanel.mainInstance().identify(distinctId: userId)
        Mixpanel.mainInstance().people.set(properties: properties.asMixpanelProperties)
    }

    public func incrementUserProperties(incrementalProperties: [String: Any]) {
        Mixpanel.mainInstance().people.increment(properties: incrementalProperties.asMixpanelProperties)
    }

    public func logout() {
        Mixpanel.mainInstance().reset()
    }

    public func setSuperProperties(properties: [String: Any]) {
        Mixpanel.mainInstance().registerSuperProperties(properties.asMixpanelProperties)
    }

    public func setUserProperties(properties: [String: Any]) {
        Mixpanel.mainInstance().people.set(properties: properties.asMixpanelProperties)
    }

    public func trackEvent(event: AnalyticsEvent, properties: [String: Any]) {
        Mixpanel.mainInstance().track(event: event.name, properties: properties.asMixpanelProperties)
    }

    public func unsetAllSuperProperties() {
        Mixpanel.mainInstance().clearSuperProperties()
    }

    public func unsetSuperProperties(propertyNames: [String]) {
        propertyNames.forEach { property in
            Mixpanel.mainInstance().unregisterSuperProperty(property)
        }
    }

    public var name = "MixpanelAnalytics"
}

// This is needed to convert Kotlin type to Swift types for Mixpanel to uses these values
extension Dictionary where Key == String {
    var asMixpanelProperties: Properties {
        return compactMapValues { value in
            if let intValue = value as? Int {
                return intValue
            }
            else if let doubleValue = value as? Double {
                return doubleValue
            }
            else if let stringValue = value as? String {
                return stringValue
            }
            else {
                return "\(value)"
            }
        }
    }
}
