Pod::Spec.new do |spec|
    spec.name                     = 'TRIKOT_FRAMEWORK_NAME'
    spec.version                  = '1.0.0'
    spec.homepage                 = 'www.mirego.com'
    spec.source                   = { :git => "Not Published", :tag => "Cocoapods/#{spec.name}/#{spec.version}" }
    spec.authors                  = ''
    spec.license                  = { :type => 'BSD-3' }
    spec.summary                  = 'trikot.kword Sample'

    spec.static_framework         = true
    spec.vendored_frameworks      = "build/bin/ios/TRIKOT_FRAMEWORK_NAME.framework"
    spec.libraries                = "c++", "System"
    spec.module_name              = "#{spec.name}_umbrella"

    spec.ios.deployment_target  = '13.0'

    spec.pod_target_xcconfig = {
        'KOTLIN_BUILD_TYPE[config=Debug]' => 'DEBUG',
        'KOTLIN_BUILD_TYPE[config=Release]' => 'RELEASE',
        'KOTLIN_TARGET[sdk=iphonesimulator*]' => 'iosSimulator',
        'KOTLIN_TARGET[sdk=iphoneos*]' => 'iosArm64'
    }

    spec.prepare_command = <<-CMD
        mkdir -p build/bin/ios/TRIKOT_FRAMEWORK_NAME.framework
    CMD

    spec.script_phases = [
        {
            :name => 'Build common',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
if [ "$ENABLE_PREVIEWS" = "NO" ]
then
    cd "$SRCROOT/../../../.."

    if [ "$KOTLIN_TARGET" = "iosSimulator" ]
    then
        if [ "$NATIVE_ARCH_ACTUAL" = "x86_64" ]
        then
            KOTLIN_TARGET="iosX64"
        else
            KOTLIN_TARGET="iosSimulatorArm64"
        fi
    fi

    echo "Building common framework on architecture ${KOTLIN_TARGET}"
    
    ./gradlew :trikot-kword:sample:common:copyFramework -Pconfiguration.build.dir="build/bin/ios" -Pkotlin.build.type="$KOTLIN_BUILD_TYPE" -Pkotlin.target="$KOTLIN_TARGET"
else
    echo "Skipping build of common framework in preview mode"
fi
            SCRIPT
        }
    ]
end
