Pod::Spec.new do |spec|
  spec.name          = "Trikot.http"
  spec.version       = "0.0.1"
  spec.summary       = "Trikot.http swift extensions."
  spec.description   = "Trikot.http swift extensions."
  spec.homepage      = "https://github.com/mirego/trikot.http"
  spec.license       = "MIT license"
  spec.author        = { "Martin Gagnon" => "mgagnon@mirego.com" }
  spec.source        = { :git => "https://github.com/mirego/trikot.http.git", :tag => "#{spec.version}" }
  spec.source_files  = "swift-extensions/*.swift"
  spec.static_framework = true

  spec.dependency ENV['TRIKOT_FRAMEWORK_NAME']
  spec.dependency 'ReachabilitySwift', '~> 4.3.1'

  spec.prepare_command = <<-CMD
    sed -i '' "s/TRIKOT_FRAMEWORK_NAME/${TRIKOT_FRAMEWORK_NAME}/g" ./**/*.swift
  CMD
end
