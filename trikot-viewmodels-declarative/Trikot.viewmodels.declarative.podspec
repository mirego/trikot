Pod::Spec.new do |spec|
  properties = load_properties('gradle.properties')

  spec.name          = "Trikot.viewmodels.declarative"
  spec.version       = "1.0.0"
  spec.summary       = "Plugin for trikot.viewmodels.declarative"
  spec.description   = "Plugin for trikot.viewmodels.declarative"
  spec.homepage      = "https://github.com/mirego/trikot.viewmodels.declarative"
  spec.license       = "MIT license"
  spec.author        = { "Antoine Lamy" => "alamy@mirego.com" }
  spec.source        = { :git => "https://github.com/mirego/trikot.viewmodels.declarative.git", :tag => "#{spec.version}" }

  spec.default_subspecs = 'Core', 'SwiftUI'

  spec.static_framework = true

  spec.dependency 'Trikot.streams'
  spec.dependency ENV['TRIKOT_FRAMEWORK_NAME']

  spec.subspec 'Core' do |core|
    core.source_files = 'swift/core/**/*.swift'
  end

  spec.subspec 'Combine' do |core|
    core.source_files = 'swift/combine/**/*.swift'
  end

  spec.subspec 'UIKit' do |uikit|
    uikit.source_files = 'swift/uikit/**/*.swift'
    uikit.dependency 'Trikot.viewmodels.declarative/Core'
    uikit.dependency 'Kingfisher', '>= 5.0'
  end

  spec.subspec 'SwiftUI' do |swiftui|
    swiftui.source_files = 'swift/swiftui/**/*.swift'
    swiftui.dependency 'Trikot.viewmodels.declarative/Core'
    swiftui.dependency 'Trikot.viewmodels.declarative/Combine'
    swiftui.dependency 'Kingfisher', '~> 7.1'
    swiftui.dependency 'Introspect', '~> 0.1'
  end

  spec.prepare_command = <<-CMD
    find . -type f -name "*.swift" -exec sed -i '' -e "s/TRIKOT_FRAMEWORK_NAME/${TRIKOT_FRAMEWORK_NAME}/g" {} +
  CMD
end
