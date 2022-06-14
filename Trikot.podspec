require_relative 'podspec_versions.rb'

properties = load_properties('gradle.properties')

Pod::Spec.new do |spec|
  spec.name          = "Trikot"
  spec.version       = "#{properties['version']}"
  spec.summary       = "Plugins for Trikot"
  spec.description   = "Plugins for Trikot"
  spec.homepage      = "https://github.com/mirego/trikot"
  spec.license       = "MIT license"
  spec.author        = { "Nicolas Presseault" => "npresseault@mirego.com" }
  spec.source        = { :git => "https://github.com/mirego/trikot.git", :tag => "#{spec.version}" }

  spec.static_framework = true

  spec.dependency ENV['TRIKOT_FRAMEWORK_NAME']

  # Streams
  spec.subspec 'streams' do |subspec|
    subspec.source_files  = "trikot-streams/swift-extensions/*.swift"
    subspec.ios.deployment_target = '8.0'
    subspec.tvos.deployment_target = '9.0'
  end

  spec.subspec 'streams.Combine' do |subspec|
    subspec.source_files = 'trikot-streams/swift-extensions/combine/*.swift'
    subspec.ios.deployment_target = '13.0'
  end

  # Http
  spec.subspec 'http' do |subspec|
    subspec.source_files = 'trikot-http/swift-extensions/*.swift'
    subspec.dependency 'ReachabilitySwift', '~> 5.0'
  end

  # View Model
  spec.subspec 'viewmodels' do |subspec|
    subspec.source_files  = "trikot-viewmodels/swift-extensions/*.swift"
    subspec.tvos.source_files = "trikot-viewmodels/swift-extensions/*.swift"
    subspec.tvos.exclude_files = "trikot-viewmodels/swift-extensions/UISliderExtensions.swift", "trikot-viewmodels/swift-extensions/UISwitchExtensions.swift", "trikot-viewmodels/swift-extensions/UIPickerExtensions.swift"
    subspec.ios.deployment_target = '11.0'
    subspec.tvos.deployment_target = '11.0'
    subspec.dependency 'Trikot/streams'
  end

  spec.subspec 'viewmodels.Kingfisher' do |subspec|
    subspec.source_files = 'trikot-viewmodels/swift-extensions/kingfisher/*.swift'
    subspec.tvos.source_files = 'trikot-viewmodels/swift-extensions/kingfisher/*.swift'
    subspec.ios.deployment_target = '11.0'
    subspec.tvos.deployment_target = '11.0'
    subspec.dependency 'Trikot/streams'
    subspec.dependency 'Trikot/viewmodels'
    subspec.dependency 'Kingfisher', '>= 5.0'
  end

  # View Model Declarative
  spec.subspec 'viewmodels.declarative' do |subspec|
    subspec.source_files = 'trikot-viewmodels-declarative/swift/core/**/*.swift'
    subspec.dependency 'Trikot/streams'
  end

  spec.subspec 'viewmodels.declarative.Combine' do |subspec|
    subspec.source_files = 'trikot-viewmodels-declarative/swift/combine/**/*.swift'
  end

  spec.subspec 'viewmodels.declarative.UIKit' do |subspec|
    subspec.source_files = 'trikot-viewmodels-declarative/swift/uikit/**/*.swift'
    subspec.dependency 'Trikot/streams'
    subspec.dependency 'Trikot/viewmodels.declarative'
    subspec.dependency 'Kingfisher', '>= 5.0'
  end

  spec.subspec 'viewmodels.declarative.SwiftUI' do |subspec|
    subspec.source_files = 'trikot-viewmodels-declarative/swift/swiftui/**/*.swift'
    subspec.dependency 'Trikot/viewmodels.declarative'
    subspec.dependency 'Trikot/viewmodels.declarative.Combine'
    subspec.dependency 'Kingfisher', '~> 7.1'
    subspec.dependency 'Introspect', '~> 0.1'
  end

    # View Model Declarative Flow
    spec.subspec 'viewmodels.declarative.flow' do |subspec|
      subspec.source_files = 'trikot-viewmodels-declarative-flow/swift/core/**/*.swift'
    end

    spec.subspec 'viewmodels.declarative.Combine.flow' do |subspec|
      subspec.source_files = 'trikot-viewmodels-declarative-flow/swift/combine/**/*.swift'
    end

    spec.subspec 'viewmodels.declarative.UIKit.flow' do |subspec|
      subspec.source_files = 'trikot-viewmodels-declarative-flow/swift/uikit/**/*.swift'
      subspec.dependency 'Trikot/viewmodels.declarative.flow'
      subspec.dependency 'Kingfisher', '>= 5.0'
    end

    spec.subspec 'viewmodels.declarative.SwiftUI.flow' do |subspec|
      subspec.source_files = 'trikot-viewmodels-declarative-flow/swift/swiftui/**/*.swift'
      subspec.dependency 'Trikot/viewmodels.declarative.flow'
      subspec.dependency 'Trikot/viewmodels.declarative.Combine.flow'
      subspec.dependency 'Kingfisher', '~> 7.1'
      subspec.dependency 'Introspect', '~> 0.1'
    end

  # Analytics
  spec.subspec 'analytics.Firebase' do |subspec|
    subspec.source_files = 'trikot-analytics/swift-extensions/firebase/*.swift'
    subspec.ios.deployment_target  = '10.0'
    subspec.dependency 'Firebase/Analytics'
  end

  spec.subspec 'analytics.Mixpanel' do |subspec|
    subspec.source_files = 'trikot-analytics/swift-extensions/mixpanel/*.swift'
    subspec.ios.deployment_target  = '10.0'
    subspec.dependency 'Mixpanel-swift'
  end

  # Kword
  spec.subspec 'kword' do |subspec|
    subspec.source_files  = "trikot-kword/swift-extensions/*.swift"
  end

  # Bluetooth
  spec.subspec 'bluetooth' do |subspec|
    subspec.source_files  = "trikot-bluetooth/swift-extensions/*.swift"
    subspec.ios.deployment_target  = '10.0'
    subspec.dependency ENV['TRIKOT_FRAMEWORK_NAME']
  end

  spec.prepare_command = <<-CMD
    find . -type f -name "*.swift" -exec sed -i '' -e "s/TRIKOT_FRAMEWORK_NAME/${TRIKOT_FRAMEWORK_NAME}/g" {} +
  CMD
end
