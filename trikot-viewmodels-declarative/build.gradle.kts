plugins {
    id("mirego.release").version("2.0")
    id("mirego.publish").version("1.0")
}

release {
    checkTasks = listOf(
        ":viewmodels-declarative:check",
        ":compose:check"
    )
    buildTasks = listOf(
        ":viewmodels-declarative:publish",
        ":compose:publish"
    )
    updateVersionPart = 2
}
