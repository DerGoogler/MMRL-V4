package com.sanmer.mrepo.utils.extensions

inline fun <reified T> List<List<T>>.merge(): List<T> {
    val values = mutableListOf<T>()
    forEach { values.addAll(it) }
    return values
}