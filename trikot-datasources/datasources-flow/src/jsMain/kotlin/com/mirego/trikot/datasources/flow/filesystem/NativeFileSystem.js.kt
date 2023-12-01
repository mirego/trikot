package com.mirego.trikot.datasources.flow.filesystem

import okio.FileSystem
import okio.NodeJsFileSystem

internal actual object NativeFileSystem {
    actual val fileSystem: FileSystem = NodeJsFileSystem
}
