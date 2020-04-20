Pod::Spec.new do |spec|
  spec.name          = "Trikot.analytics"
  spec.version       = "0.0.1"
  spec.summary       = "Trikot.analytics ios Manager."
  spec.description   = "Trikot.analytics ios Manager."
  spec.homepage      = "https://github.com/mirego/trikot.analytics"
  spec.license       = "MIT license"
  spec.author        = { "Martin Gagnon" => "mgagnon@mirego.com" }
  spec.source        = { :git => "https://github.com/mirego/trikot.analytics.git", :tag => "#{spec.version}" }
  spec.source_files  = "swift-extensions/*.swift"
  spec.static_framework = true

  spec.ios.deployment_target  = '10.0'
  spec.dependency ENV['TRIKOT_FRAMEWORK_NAME']

  spec.subspec 'Firebase' do |firebase|
    firebase.source_files = 'swift-extensions/firebase/*.swift'
    firebase.dependency 'Firebase/Analytics'
  end

  spec.subspec 'Mixpanel' do |mixpanel|
    mixpanel.source_files = 'swift-extensions/mixpanel/*.swift'
    mixpanel.dependency 'Mixpanel-swift'
  end

  spec.prepare_command = <<-CMD
    sed -i '' "s/TRIKOT_FRAMEWORK_NAME/${TRIKOT_FRAMEWORK_NAME}/g" ./**/**/*.swift
  CMD
end
