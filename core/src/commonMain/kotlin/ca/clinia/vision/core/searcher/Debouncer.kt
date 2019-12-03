package ca.clinia.vision.core.searcher

import kotlinx.coroutines.*


public class Debouncer(
    public val debounceTimeInMillis: Long
) {
    public var job: Job? = null

    public fun debounce(
        coroutineScope: CoroutineScope,
        block: suspend () -> Unit
    ) {
        job?.cancel()
        job = coroutineScope.launch {
            delay(debounceTimeInMillis)
            block()
        }
    }

    public fun <R> debounce(searcher: Searcher<R>, block: suspend Searcher<R>.() -> Unit) {
        debounce(searcher.coroutineScope) { block(searcher) }
    }

    public fun <R> debounce(searcherPlaces: SearcherPlaces<R>, block: suspend SearcherPlaces<R>.() -> Unit) {
        debounce(searcherPlaces.coroutineScope) { block(searcherPlaces) }
    }

    public fun <R> debounce(searcherQuerySuggestions: SearcherQuerySuggestions<R>, block: suspend SearcherQuerySuggestions<R>.() -> Unit) {
        debounce(searcherQuerySuggestions.coroutineScope) { block(searcherQuerySuggestions) }
    }
}