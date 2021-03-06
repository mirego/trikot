/*
 * Copyright 2016-2021 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

package kotlinx.coroutines.reactive

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlin.test.Test

class CancelledParentAttachTest : TestBase() {

    @Test
    fun testFlow() = runTest {
        val f = flowOf(1, 2, 3).cancellable()
        val j = Job().also { it.cancel() }
        f.asPublisher(j).asFlow().collect()
    }
}
