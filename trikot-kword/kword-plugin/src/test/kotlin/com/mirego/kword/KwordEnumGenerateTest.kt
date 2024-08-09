package com.mirego.kword

import org.gradle.internal.impldep.org.apache.ivy.plugins.repository.ResourceHelper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class KwordPluginTest {

    @Rule
    @JvmField
    var temporaryFolder: TemporaryFolder = TemporaryFolder()

    private val kWordEnumGenerate = KWordEnumGenerate()
}