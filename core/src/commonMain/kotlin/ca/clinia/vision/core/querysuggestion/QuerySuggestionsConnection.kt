package ca.clinia.vision.core.querysuggestion

import ca.clinia.vision.core.connection.Connection
import ca.clinia.vision.core.searcher.SearcherQuerySuggestions

public fun <R, T> SearcherQuerySuggestions<R>.connectSuggestionsView(
    adapater: QuerySuggestionsView<T>,
    transform: (R) -> List<T>
) : Connection {
    return QuerySuggestionsConnectionView(
        this,
        adapater,
        transform
    )
}