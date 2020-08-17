Pod::Spec.new do |spec|
  spec.name          = "Trikot.streams"
  spec.version       = "0.0.1"
  spec.summary       = "Manage Trikot.streams publisher subscription lifecycle."
  spec.description   = "Manage Trikot.streams publisher subscription lifecycle."
  spec.homepage      = "https://github.com/mirego/trikot.streams"
  spec.license       = "MIT license"
  spec.author        = { "Martin Gagnon" => "mgagnon@mirego.com" }
  spec.source        = { :git => "https://github.com/mirego/trikot.streams.git", :tag => "#{spec.version}" }
  spec.static_framework = true
  
  spec.dependency ENV['TRIKOT_FRAMEWORK_NAME']

  spec.default_subspec = 'streams'
  
  spec.subspec 'streams' do |ss|
    ss.source_files  = "swift-extensions/*.swift"
    ss.ios.deployment_target = '8.0'
    ss.tvos.deployment_target = '9.0'
  end

  spec.subspec 'Combine' do |combine|
    combine.ios.deployment_target = '13.0'
    combine.source_files = 'swift-extensions/combine/*.swift'
  end

  spec.prepare_command = <<-CMD
  sed -i '' "s/TRIKOT_FRAMEWORK_NAME/${TRIKOT_FRAMEWORK_NAME}/g" ./**/*.swift
  sed -i '' "s/TRIKOT_FRAMEWORK_NAME/${TRIKOT_FRAMEWORK_NAME}/g" ./**/**/*.swift
  CMD
end
