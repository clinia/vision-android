package ca.clinia.vision.helper.searcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


internal actual val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

internal actual val osVersion = "JVM (${System.getProperty("java.version")})"