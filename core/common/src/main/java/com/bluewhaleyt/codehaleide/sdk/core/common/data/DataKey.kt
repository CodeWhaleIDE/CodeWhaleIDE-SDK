package com.bluewhaleyt.codehaleide.sdk.core.common.data

import org.jetbrains.annotations.ApiStatus

@ApiStatus.Internal
class DataKey <T> private constructor(val name: String) {
    companion object {
        fun <T> create(name: String) = DataKey<T>(name)
    }
}