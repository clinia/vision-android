package ca.clinia.vision.core.querysuggestion

import ca.clinia.vision.core.connection.Connection
import ca.clinia.vision.core.searcher.SearcherQuerySuggestions

public fun QuerySuggestionViewModel.connectView(
    view: QuerySuggestionBoxView
) : Connection {
    return QuerySuggestionBoxConnectionView(
        this,
        view
    )
}