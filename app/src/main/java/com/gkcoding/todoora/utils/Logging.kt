package com.gkcoding.todoora.utils

import android.util.Log

interface Logger {

    var minLevel: Level

    fun log(tag: String, level: Level, message: String? = null, throwable: Throwable? = null)

    enum class Level {
        Verbose, Debug, Info, Warn, Error,
    }
}

class DebugLogger(
    override var minLevel: Logger.Level = Logger.Level.Debug,
) : Logger {

    override fun log(tag: String, level: Logger.Level, message: String?, throwable: Throwable?) {
        if (message != null) {
            println(level, tag, message)
        }

        if (throwable != null) {
            println(level, tag, throwable.stackTraceToString())
        }
    }
}

fun Logger.log(tag: String, throwable: Throwable) {
    if (minLevel <= Logger.Level.Error) {
        log(tag, Logger.Level.Error, null, throwable)
    }
}

inline fun Logger.log(tag: String, level: Logger.Level, message: () -> String) {
    if (minLevel <= level) {
        log(tag, level, message(), null)
    }
}

internal fun println(level: Logger.Level, tag: String, message: String) {
    Log.println(level.toInt(), tag, message)
}

private fun Logger.Level.toInt() = when (this) {
    Logger.Level.Verbose -> Log.VERBOSE
    Logger.Level.Debug -> Log.DEBUG
    Logger.Level.Info -> Log.INFO
    Logger.Level.Warn -> Log.WARN
    Logger.Level.Error -> Log.ERROR
}