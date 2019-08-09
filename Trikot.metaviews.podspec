Pod::Spec.new do |spec|
  spec.name          = "Trikot.metaviews"
  spec.version       = "0.0.1"
  spec.summary       = "Plugin for trikot.metaviews."
  spec.description   = "Plugin for trikot.metaviews."
  spec.homepage      = "https://github.com/mirego/trikot.metaviews"
  spec.license       = "MIT license"
  spec.author        = { "Martin Gagnon" => "mgagnon@mirego.com" }
  spec.source        = { :git => "https://github.com/mirego/trikot.metaviews.git", :tag => "#{spec.version}" }
  spec.source_files  = "swift-extensions/*.swift"
  
  spec.static_framework = true
  
  spec.dependency 'Trikot.streams'
  spec.dependency ENV['TRIKOT_FRAMEWORK_NAME']

  spec.prepare_command = <<-CMD
    sed -i '' "s/TRIKOT_FRAMEWORK_NAME/${TRIKOT_FRAMEWORK_NAME}/g" ./**/*.swift
  CMD
end
