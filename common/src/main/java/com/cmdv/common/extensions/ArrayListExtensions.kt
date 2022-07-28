package com.cmdv.common.extensions

/**
 * Extension function.
 */
fun <T> ArrayList<T>.addAllNoRepeated(items: List<T>) {
    items.forEach { newElement ->
        if (!this.contains(newElement)) {
            this.add(newElement)
        }
    }
}