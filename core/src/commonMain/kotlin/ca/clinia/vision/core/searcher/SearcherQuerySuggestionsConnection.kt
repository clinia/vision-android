package ca.clinia.vision.core.searcher

import ca.clinia.vision.core.connection.Connection

public fun <T, R> SearcherQuerySuggestions<R>.connectView(view: (T) -> Unit, transform: (R?) -> T): Connection {
    return SearcherQuerySuggestionsConnectionView(this, view, transform)
}