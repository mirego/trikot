import Mixpanel
import UIKit
import TRIKOT_FRAMEWORK_NAME

class MixpanelAnalyticsService: AnalyticsService {
    func identifyUser(userId: String, properties: [String: Any]) {
        Mixpanel.mainInstance().identify(distinctId: userId)
        Mixpanel.mainInstance().people.set(properties: properties.asMixpanelProperties)
    }

    func incrementUserProperties(incrementalProperties: [String: Any]) {
        Mixpanel.mainInstance().people.increment(properties: incrementalProperties.asMixpanelProperties)
    }

    func logout() {
        Mixpanel.mainInstance().reset()
    }

    func setSuperProperties(properties: [String: Any]) {
        Mixpanel.mainInstance().registerSuperProperties(properties.asMixpanelProperties)
    }

    func trackEvent(event: AnalyticsEvent, properties: [String: Any]) {
        Mixpanel.mainInstance().track(event: event.name, properties: properties.asMixpanelProperties)
    }

    func unsetAllSuperProperties() {
        Mixpanel.mainInstance().clearSuperProperties()
    }

    func unsetSuperProperties(propertyNames: [String]) {
        propertyNames.forEach { property in
            Mixpanel.mainInstance().unregisterSuperProperty(property)
        }
    }

    var name = "MixpanelAnalytics"
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
