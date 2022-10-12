Pod::Spec.new do |spec|
    spec.name                     = 'TRIKOT_FRAMEWORK_NAME'
    spec.version                  = '4.0.0-dev1-SNAPSHOT'
    spec.homepage                 = 'www.mirego.com'
    spec.source                   = { :http=> ''}
    spec.authors                  = ''
    spec.license                  = 'BSD-3'
    spec.summary                  = 'Trikot-viewmodels-declarative sample'
    spec.vendored_frameworks      = 'build/cocoapods/framework/TRIKOT_FRAMEWORK_NAME.framework'
    spec.libraries                = 'c++'
                
                
                
    spec.pod_target_xcconfig = {
        'KOTLIN_PROJECT_PATH' => ':trikot-viewmodels-declarative-flow:sample:common',
        'PRODUCT_MODULE_NAME' => 'TRIKOT_FRAMEWORK_NAME',
    }
                
    spec.script_phases = [
        {
            :name => 'Build TRIKOT_FRAMEWORK_NAME',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
                if [ "YES" = "$COCOAPODS_SKIP_KOTLIN_BUILD" ]; then
                  echo "Skipping Gradle build task invocation due to COCOAPODS_SKIP_KOTLIN_BUILD environment variable set to \"YES\""
                  exit 0
                fi
                set -ev
                REPO_ROOT="$PODS_TARGET_SRCROOT"
                "$REPO_ROOT/../../../gradlew" -p "$REPO_ROOT" $KOTLIN_PROJECT_PATH:syncFramework \
                    -Pkotlin.native.cocoapods.platform=$PLATFORM_NAME \
                    -Pkotlin.native.cocoapods.archs="$ARCHS" \
                    -Pkotlin.native.cocoapods.configuration="$CONFIGURATION"
            SCRIPT
        }
    ]
    spec.resources = "src/commonMain/resources/translations/*"
end