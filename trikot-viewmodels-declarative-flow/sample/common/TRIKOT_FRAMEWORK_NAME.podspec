Pod::Spec.new do |spec|
    spec.name                     = 'TRIKOT_FRAMEWORK_NAME'
    spec.version                  = '5.4.0-SNAPSHOT'
    spec.homepage                 = 'www.mirego.com'
    spec.source                   = { :http=> ''}
    spec.authors                  = ''
    spec.license                  = 'BSD-3'
    spec.summary                  = 'Trikot-viewmodels-declarative sample'
    spec.vendored_frameworks      = 'build/cocoapods/framework/TRIKOT_FRAMEWORK_NAME.framework'
    spec.libraries                = 'c++'
                
                
                
    if !Dir.exist?('build/cocoapods/framework/TRIKOT_FRAMEWORK_NAME.framework') || Dir.empty?('build/cocoapods/framework/TRIKOT_FRAMEWORK_NAME.framework')
        raise "

        Kotlin framework 'TRIKOT_FRAMEWORK_NAME' doesn't exist yet, so a proper Xcode project can't be generated.
        'pod install' should be executed after running ':generateDummyFramework' Gradle task:

            ./gradlew :trikot-viewmodels-declarative-flow:sample:common:generateDummyFramework

        Alternatively, proper pod installation is performed during Gradle sync in the IDE (if Podfile location is set)"
    end
                
    spec.xcconfig = {
        'ENABLE_USER_SCRIPT_SANDBOXING' => 'NO',
    }
                
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
                if [ "YES" = "$OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED" ]; then
                  echo "Skipping Gradle build task invocation due to OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED environment variable set to \"YES\""
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
    spec.prepare_command = <<-CMD
    ../../../gradlew :trikot-viewmodels-declarative-flow:sample:common:generateDummyFramework
CMD
end