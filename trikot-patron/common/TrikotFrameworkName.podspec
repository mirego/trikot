Pod::Spec.new do |spec|
    spec.name                     = 'TrikotFrameworkName'
    spec.version                  = '0.0.1'
    spec.homepage                 = 'www.mirego.com'
    spec.source                   = { :git => "Not Published", :tag => "Cocoapods/#{spec.name}/#{spec.version}" }
    spec.authors                  = ''
    spec.license                  = 'No license'
    spec.summary                  = 'Awesome library'

    spec.static_framework         = true
    spec.vendored_frameworks      = "build/bin/TrikotFrameworkName.framework"
    spec.libraries                = "c++", "System"
    spec.module_name              = "#{spec.name}_umbrella"

    spec.pod_target_xcconfig = {
        'KOTLIN_BUILD_TYPE[config=Debug]' => 'DEBUG',
        'KOTLIN_BUILD_TYPE[config=Release]' => 'RELEASE',
        'KOTLIN_TARGET[sdk=iphonesimulator*]' => 'iosX64',
        'KOTLIN_TARGET[sdk=iphoneos*]' => 'iosArm64',
        'KOTLIN_TARGET[sdk=appletvos*]' => 'tvosArm64',
        'KOTLIN_TARGET[sdk=appletvsimulator*]' => 'tvosX64'
    }

    spec.prepare_command = <<-CMD
        ../gradlew :common:copyFramework -Pconfiguration.build.dir="build/bin/ios"
    CMD

    spec.script_phases = [
        {
            :name => 'Build common',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
if [ "$ENABLE_PREVIEWS" = "NO" ]
then
  echo "Building common framework"

  cd "$SRCROOT/../.."
  pwd
  FILE=.env.sh
  if [ -f $FILE ]; then
    source .env.sh
  fi

  ./gradlew :common:copyFramework \
    -Pconfiguration.build.dir="build/bin" \
    -Pkotlin.build.type="$KOTLIN_BUILD_TYPE" \
    -Pkotlin.target="$KOTLIN_TARGET"
else
  echo "Skipping build of common framework in preview mode"
fi
            SCRIPT
        }
    ]
end
