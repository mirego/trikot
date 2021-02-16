Pod::Spec.new do |spec|
  spec.name          = "Trikot.viewmodels.declarative"
  spec.version       = "0.0.1"
  spec.summary       = "Plugin for trikot.viewmodels.declarative"
  spec.description   = "Plugin for trikot.viewmodels.declarative"
  spec.homepage      = "https://github.com/mirego/trikot.viewmodels.declarative"
  spec.license       = "MIT license"
  spec.author        = { "Antoine Lamy" => "alamy@mirego.com" }
  spec.source        = { :git => "https://github.com/mirego/trikot.viewmodels.declarative.git", :tag => "#{spec.version}" }
  spec.source_files  = "swift-extensions/*.swift"
  spec.tvos.source_files = "swift-extensions/*.swift"

  spec.static_framework = true

  spec.dependency 'Trikot.streams'
  spec.dependency ENV['TRIKOT_FRAMEWORK_NAME']

  spec.prepare_command = <<-CMD
    sed -i '' "s/TRIKOT_FRAMEWORK_NAME/${TRIKOT_FRAMEWORK_NAME}/g" ./**/*.swift
  CMD
end
