package ca.clinia.vision.helper.querysuggestion

import ca.clinia.vision.core.connection.Connection
import ca.clinia.vision.core.querysuggestion.QuerySuggestionBoxView
import ca.clinia.vision.core.querysuggestion.QuerySuggestionViewModel
import ca.clinia.vision.core.querysuggestion.connectView
import ca.clinia.vision.core.searcher.*

public fun <R, RQS> QuerySuggestionViewModel.connectSearcher(
    searcher: Searcher<R>,
    searcherQuerySuggestions: SearcherQuerySuggestions<RQS>,
    debouncer: Debouncer = Debouncer(debouncePlacesInMillis)
) : Connection {
    return QuerySuggestionConnectionSearcher(
        this,
        searcher,
        searcherQuerySuggestions,
        debouncer
    )
}

public fun <R, RQS> QuerySuggestionConnector<R, RQS>.connectView(
    view: QuerySuggestionBoxView
): Connection {
    return viewModel.connectView(view)
}