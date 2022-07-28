package com.cmdv.common.extensions

private const val HTTP = "http://"
private const val SECURE_HTTP = "https://"

fun String.secureUrl(): String = this.replace(HTTP, SECURE_HTTP)