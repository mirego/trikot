Pod::Spec.new do |spec|
  spec.name          = "Trikot.bluetooth"
  spec.version       = "0.0.1"
  spec.summary       = "Trikot.bluetooth ios Manager."
  spec.description   = "Trikot.bluetooth ios Manager."
  spec.homepage      = "https://github.com/mirego/trikot.bluetooth"
  spec.license       = "MIT license"
  spec.author        = { "Martin Gagnon" => "mgagnon@mirego.com" }
  spec.source        = { :git => "https://github.com/mirego/trikot.bluetooth.git", :tag => "#{spec.version}" }
  spec.source_files  = "swift-extensions/*.swift"
  spec.static_framework = true
  
  spec.ios.deployment_target  = '10.0'
  spec.dependency ENV['TRIKOT_FRAMEWORK_NAME']

  spec.prepare_command = <<-CMD
    sed -i '' "s/TRIKOT_FRAMEWORK_NAME/${TRIKOT_FRAMEWORK_NAME}/g" ./**/*.swift
  CMD
end
