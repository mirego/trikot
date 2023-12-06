package com.mirego.trikot.datasources.flow.filesystem

import okio.FileSystem

internal actual object NativeFileSystem {
    actual val fileSystem: FileSystem = FileSystem.SYSTEM
}
