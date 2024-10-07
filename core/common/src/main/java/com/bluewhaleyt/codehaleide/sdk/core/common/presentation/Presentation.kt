package com.bluewhaleyt.codehaleide.sdk.core.common.presentation

interface Presentation {
    val info: PresentationInfo

    companion object {
        internal val EMPTY = object : Presentation {
            override val info = object : PresentationInfo {}
        }
    }
}