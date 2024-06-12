package dev.tarna.nomorephantoms.api.utils

import net.kyori.adventure.text.minimessage.MiniMessage

val mm = MiniMessage.miniMessage()

fun String.color() = mm.deserialize(this)

fun List<String>.color() = map { it.color() }