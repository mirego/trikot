package com.mirego.trikot.datasources.flow.filesystem

import okio.FileSystem

@Suppress("NO_ACTUAL_FOR_EXPECT")
internal expect object NativeFileSystem {
    val fileSystem: FileSystem
}
