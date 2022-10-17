# Trikot.datasources.flow

## Installation

##### Import dependencies

```groovy
    dependencies {
        maven { url("https://s3.amazonaws.com/mirego-maven/public") }
    }

    ios() {
        binaries {
            framework {
                export "com.mirego.trikot:datasources-flow:$trikot_version"
            }
        }
    }
    sourceSets {
        commonMain {
            dependencies {
                 implementation "com.mirego.trikot:datasources-flow:$trikot_version"
            }
        }
    }
```
