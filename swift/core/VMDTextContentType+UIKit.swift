import UIKit
import TRIKOT_FRAMEWORK_NAME

extension VMDTextContentType {
    public var uiTextContentType: UITextContentType? {
        switch self {
        case .name:
            return .name
        case .nameprefix:
            return .namePrefix
        case .givenname:
            return .givenName
        case .middlename:
            return .middleName
        case .familyname:
            return .familyName
        case .namesuffix:
            return .nameSuffix
        case .nickname:
            return .nickname
        case .jobtitle:
            return .jobTitle
        case .organizationname:
            return .organizationName
        case .location:
            return .location
        case .fullstreetaddress:
            return .fullStreetAddress
        case .streetaddressline1:
            return .streetAddressLine1
        case .streetaddressline2:
            return .streetAddressLine2
        case .addresscity:
            return .addressCity
        case .addressstate:
            return .addressState
        case .addresscityandstate:
            return .addressCityAndState
        case .sublocality:
            return .sublocality
        case .countryname:
            return .countryName
        case .postalcode:
            return .postalCode
        case .telephonenumber:
            return .telephoneNumber
        case .emailaddress:
            return .emailAddress
        case .url:
            return .URL
        case .creditcardnumber:
            return .creditCardNumber
        case .username:
            return .username
        case .password:
            return .password
        case .newpassword:
            return .newPassword
        case .onetimecode:
            return .oneTimeCode
        default:
            return nil
        }
    }
}
