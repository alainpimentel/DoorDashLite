package com.alainp.doordashlite.utilities

import java.time.LocalDateTime
import java.time.ZoneOffset

fun String.toEpochMs(): Long {
    return LocalDateTime
        .parse(this)
        .toEpochSecond(ZoneOffset.UTC) * 1000
}