package com.bluewhaleyt.codehaleide.sdk.core.common.data

import androidx.compose.runtime.staticCompositionLocalOf

fun <T> DataContext.require(keyName: String) =
    get<T>(keyName) ?: error("No instance provided for key $keyName")

fun <T> DataContext.require(key: DataKey<T>) = require<T>(key.name)

val LocalDataContext =
    staticCompositionLocalOf<DataContext> { error("No LocalDataContext provided") }