# Releases steps

1. Checkout `origin/master`.
1. Update the `CHANGELOG.md` file with the changes of this release (the format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).
    * Copy the template for the next unreleased version at the top.
    * Rename the previous unreleased version to the new version.
1. Update the version in `gradle.properties` and remove the `-SNAPSHOT` suffix.
1. Commit the changes.
1. Run the [Release workflow](https://github.com/mirego/trikot/actions/workflows/release.yml).
1. Create a new release on GitHub with the same tag and write release notes.
1. Bump the version in `gradle.properties` and add the `-SNAPSHOT` suffix.
1. Commit the change and push it.
