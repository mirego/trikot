package com.mirego.trikot.datasources.flow.filesystem

import okio.FileSystem

actual object NativeFileSystem {
    actual val fileSystem: FileSystem = FileSystem.SYSTEM
}
