Pod::Spec.new do |spec|
    spec.name                     = 'ViewModelsSample'
    spec.version                  = '0.0.1'
    spec.homepage                 = 'www.mirego.com'
    spec.source                   = { :git => "Not Published", :tag => "Cocoapods/#{spec.name}/#{spec.version}" }
    spec.authors                  = ''
    spec.license                  = 'No license'
    spec.summary                  = 'Awesome library'

    spec.static_framework         = true
    spec.vendored_frameworks      = "build/bin/ios/ViewModelsSample.framework"
    spec.libraries                = "c++", "System"
    spec.module_name              = "#{spec.name}_umbrella"

    spec.pod_target_xcconfig = {
        'KOTLIN_TARGET[sdk=iphonesimulator*]' => 'ios_x64',
        'KOTLIN_TARGET[sdk=iphoneos*]' => 'ios_arm',
    }

    spec.prepare_command = <<-CMD
        ../gradlew :common:copyFramework -Pkotlin.build.type=RELEASE
      CMD

    spec.script_phases = [
        {
            :name => 'Build common',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
                cd "$SRCROOT/../.." 
                pwd
                FILE=.env.sh
                if [ -f $FILE ]; then
                source .env.sh
                fi
                ./gradlew :common:copyFramework \
                -Pconfiguration.build.dir=${CONFIGURATION_BUILD_DIR} \
                -Pkotlin.build.type=${KOTLIN_BUILD_TYPE} \
                -Pkotlin.target=${KOTLIN_TARGET}
            SCRIPT
        }
    ]
end