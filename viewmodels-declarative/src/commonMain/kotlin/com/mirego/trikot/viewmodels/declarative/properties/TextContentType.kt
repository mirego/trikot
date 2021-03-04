package com.mirego.trikot.viewmodels.declarative.properties

/**
 * Constants that identify the semantic meaning for a component that supports text as input.
 *
 * [SwiftUI](https://developer.apple.com/documentation/uikit/uitextcontenttype)
 * **No equivalent in Jetpack Compose yet**
 */
enum class TextContentType {
    Name,
    NamePrefix,
    GivenName,
    MiddleName,
    FamilyName,
    NameSuffix,
    Nickname,
    JobTitle,
    OrganizationName,
    Location,
    FullStreetAddress,
    StreetAddressLine1,
    StreetAddressLine2,
    AddressCity,
    AddressState,
    AddressCityAndState,
    Sublocality,
    CountryName,
    PostalCode,
    TelephoneNumber,
    EmailAddress,
    URL,
    CreditCardNumber,
    Username,
    Password,
    NewPassword,
    OneTimeCode
}
