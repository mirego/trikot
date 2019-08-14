Pod::Spec.new do |spec|
  spec.name          = "kword"
  spec.version       = "0.0.1"
  spec.summary       = "Kword swift extensions"
  spec.description   = "Kword swift extensions"
  spec.homepage      = "https://github.com/mirego/kword"
  spec.license       = "MIT license"
  spec.author        = { "Martin Gagnon" => "mgagnon@mirego.com" }
  spec.source        = { :git => "https://github.com/mirego/kword.git", :tag => "#{spec.version}" }
  spec.source_files  = "swift-extensions/*.swift"
  spec.static_framework = true
  
  spec.dependency ENV['TRIKOT_FRAMEWORK_NAME']

  spec.prepare_command = <<-CMD
    sed -i '' "s/TRIKOT_FRAMEWORK_NAME/${TRIKOT_FRAMEWORK_NAME}/g" ./**/*.swift
  CMD
end
