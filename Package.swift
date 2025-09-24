// swift-tools-version: 5.7

import PackageDescription

let package = Package(
    name: "Trikot",
    platforms: [
        .iOS(.v12),
        .tvOS(.v12),
        .macOS(.v12)
    ],
    products: [
        .library(name: "TrikotStreams", targets: ["TrikotStreams"]),
        .library(name: "TrikotStreamsCombine", targets: ["TrikotStreamsCombine"]),
        .library(name: "TrikotHttp", targets: ["TrikotHttp"]),
        .library(name: "TrikotViewModels", targets: ["TrikotViewModels"]),
        .library(name: "TrikotViewModelsKingfisher", targets: ["TrikotViewModelsKingfisher"]),
        .library(name: "TrikotViewModelsDeclarative", targets: ["TrikotViewModelsDeclarative"]),
        .library(name: "TrikotViewModelsDeclarativeCombine", targets: ["TrikotViewModelsDeclarativeCombine"]),
        .library(name: "TrikotViewModelsDeclarativeUIKit", targets: ["TrikotViewModelsDeclarativeUIKit"]),
        .library(name: "TrikotViewModelsDeclarativeSwiftUI", targets: ["TrikotViewModelsDeclarativeSwiftUI"]),
        .library(name: "TrikotViewModelsDeclarativeFlow", targets: ["TrikotViewModelsDeclarativeFlow"]),
        .library(name: "TrikotViewModelsDeclarativeSwiftUIFlow", targets: ["TrikotViewModelsDeclarativeSwiftUIFlow"]),
        .library(name: "TrikotAnalyticsFirebase", targets: ["TrikotAnalyticsFirebase"]),
        .library(name: "TrikotAnalyticsMixpanel", targets: ["TrikotAnalyticsMixpanel"]),
        .library(name: "TrikotKword", targets: ["TrikotKword"]),
        .library(name: "TrikotBluetooth", targets: ["TrikotBluetooth"])
    ],
    dependencies: [
        .package(url: "https://github.com/ashleymills/Reachability.swift", from: "5.2.0"),
        .package(url: "https://github.com/onevcat/Kingfisher.git", from: "7.10.1"),
        .package(url: "https://github.com/firebase/firebase-ios-sdk", from: "10.0.0"),
        .package(url: "https://github.com/mixpanel/mixpanel-swift", from: "4.0.0"),
        .package(url: "https://github.com/siteline/swiftui-introspect.git", from: "1.0.0")
    ],
    targets: [
        // Streams
        .target(
            name: "TrikotStreams",
            path: "trikot-streams/swift-extensions"
        ),

        // Streams Combine
        .target(
            name: "TrikotStreamsCombine",
            dependencies: ["TrikotStreams"],
            path: "trikot-streams/swift-extensions/combine"
        ),

        // Http
        .target(
            name: "TrikotHttp",
            dependencies: [
                .product(name: "Reachability", package: "Reachability.swift")
            ],
            path: "trikot-http/swift-extensions"
        ),

        // View Models
        .target(
            name: "TrikotViewModels",
            dependencies: ["TrikotStreams"],
            path: "trikot-viewmodels/swift-extensions",
            exclude: ["UISliderExtensions.swift", "UISwitchExtensions.swift", "UIPickerExtensions.swift", "kingfisher"]
        ),

        // View Models Kingfisher
        .target(
            name: "TrikotViewModelsKingfisher",
            dependencies: [
                "TrikotStreams",
                "TrikotViewModels",
                .product(name: "Kingfisher", package: "Kingfisher")
            ],
            path: "trikot-viewmodels/swift-extensions/kingfisher"
        ),

        // View Models Declarative
        .target(
            name: "TrikotViewModelsDeclarative",
            dependencies: ["TrikotStreams"],
            path: "trikot-viewmodels-declarative/swift/core"
        ),

        // View Models Declarative Combine
        .target(
            name: "TrikotViewModelsDeclarativeCombine",
            dependencies: ["TrikotViewModelsDeclarative"],
            path: "trikot-viewmodels-declarative/swift/combine"
        ),

        // View Models Declarative UIKit
        .target(
            name: "TrikotViewModelsDeclarativeUIKit",
            dependencies: [
                "TrikotStreams",
                "TrikotViewModelsDeclarative",
                .product(name: "Kingfisher", package: "Kingfisher")
            ],
            path: "trikot-viewmodels-declarative/swift/uikit"
        ),

        // View Models Declarative SwiftUI
        .target(
            name: "TrikotViewModelsDeclarativeSwiftUI",
            dependencies: [
                "TrikotViewModelsDeclarative",
                "TrikotViewModelsDeclarativeCombine",
                .product(name: "Kingfisher", package: "Kingfisher"),
                .product(name: "SwiftUIIntrospect", package: "swiftui-introspect")
            ],
            path: "trikot-viewmodels-declarative/swift/swiftui"
        ),

        // View Models Declarative Flow
        .target(
            name: "TrikotViewModelsDeclarativeFlow",
            path: "trikot-viewmodels-declarative-flow/swift/core"
        ),

        // View Models Declarative SwiftUI Flow
        .target(
            name: "TrikotViewModelsDeclarativeSwiftUIFlow",
            dependencies: [
                "TrikotViewModelsDeclarativeFlow",
                .product(name: "Kingfisher", package: "Kingfisher"),
                .product(name: "SwiftUIIntrospect", package: "swiftui-introspect")
            ],
            path: "trikot-viewmodels-declarative-flow/swift/swiftui"
        ),

        // Analytics Firebase
        .target(
            name: "TrikotAnalyticsFirebase",
            dependencies: [
                .product(name: "FirebaseAnalytics", package: "firebase-ios-sdk")
            ],
            path: "trikot-analytics/swift-extensions/firebase"
        ),

        // Analytics Mixpanel
        .target(
            name: "TrikotAnalyticsMixpanel",
            dependencies: [
                .product(name: "Mixpanel", package: "mixpanel-swift")
            ],
            path: "trikot-analytics/swift-extensions/mixpanel"
        ),

        // Kword
        .target(
            name: "TrikotKword",
            path: "trikot-kword/swift-extensions"
        ),

        // Bluetooth
        .target(
            name: "TrikotBluetooth",
            path: "trikot-bluetooth/swift-extensions"
        )
    ]
)