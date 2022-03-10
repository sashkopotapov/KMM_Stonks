package com.opotapov.stonks

import platform.Foundation.NSProcessInfo

public actual class Platform actual constructor() {
    public actual val platform: String = NSProcessInfo.processInfo().hostName + " " + NSProcessInfo.processInfo().operatingSystemVersionString
}