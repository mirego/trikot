Pod::Spec.new do |spec|
  properties = load_properties('gradle.properties')

  spec.name          = "Trikot.viewmodels.declarative"
  spec.version       = "0.0.1"
  spec.summary       = "Plugin for trikot.viewmodels.declarative"
  spec.description   = "Plugin for trikot.viewmodels.declarative"
  spec.homepage      = "https://github.com/mirego/trikot.viewmodels.declarative"
  spec.license       = "MIT license"
  spec.author        = { "Antoine Lamy" => "alamy@mirego.com" }
  spec.source        = { :git => "https://github.com/mirego/trikot.viewmodels.declarative.git", :tag => "#{spec.version}" }
  spec.source_files  = "swift/*.swift"
  spec.tvos.source_files = "swift/*.swift"

  spec.static_framework = true

  spec.dependency ENV['TRIKOT_FRAMEWORK_NAME']

  spec.subspec 'UIKit' do |uikit|
    uikit.source_files = 'swift/uikit/*.swift'
    uikit.dependency 'Kingfisher', '~> 5.0'
    uikit.dependency 'Trikot.streams'
  end

  spec.subspec 'SwiftUI' do |swiftui|
    swiftui.source_files = 'swift/swiftui/*.swift'
  end

  spec.prepare_command = <<-CMD
    sed -i '' "s/TRIKOT_FRAMEWORK_NAME/${TRIKOT_FRAMEWORK_NAME}/g" ./**/**/*.swift
  CMD
end
