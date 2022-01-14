package com.mirego.trikot.bluetooth

import org.reactivestreams.Publisher

interface AttributeProfileService {
    val characteristics: Publisher<Map<String, AttributeProfileCharacteristic>>
}
