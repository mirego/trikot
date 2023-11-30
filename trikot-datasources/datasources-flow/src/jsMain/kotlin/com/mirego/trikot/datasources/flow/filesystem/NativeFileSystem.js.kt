package com.mirego.trikot.datasources.flow.filesystem

import okio.FileSystem
import okio.NodeJsFileSystem

actual object NativeFileSystem {
    actual val fileSystem: FileSystem = NodeJsFileSystem
}
