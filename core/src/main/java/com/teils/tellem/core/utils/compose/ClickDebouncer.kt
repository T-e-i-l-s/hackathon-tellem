package com.teils.tellem.core.utils.compose

object ClickDebouncer {
    private var lastClickTime = 0L
    private const val DEBOUNCE_DELAY = 500L

    fun canProceed(): Boolean {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime < DEBOUNCE_DELAY) {
            return false
        }
        lastClickTime = currentTime
        return true
    }
}

fun (() -> Unit).debounced(): () -> Unit {
    return {
        if (ClickDebouncer.canProceed()) {
            this()
        }
    }
}
