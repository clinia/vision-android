package ca.clinia.vision.helper.searchbox

import ca.clinia.vision.core.connection.Connection
import ca.clinia.vision.core.searchbox.SearchBoxView
import ca.clinia.vision.core.searchbox.SearchBoxViewModel
import ca.clinia.vision.core.searchbox.connectView
import ca.clinia.vision.core.searcher.Debouncer
import ca.clinia.vision.core.searcher.Searcher
import ca.clinia.vision.core.searcher.debounceSearchInMillis

public fun <R> SearchBoxViewModel.connectSearcher(
    searcher: Searcher<R>,
    searchMode: SearchMode = SearchMode.AsYouType,
    debouncer: Debouncer = Debouncer(debounceSearchInMillis)
): Connection {
    return SearchBoxConnectionSearcher(this, searcher, searchMode, debouncer)
}

public fun <R> SearchBoxConnector<R>.connectView(
    view: SearchBoxView
): Connection {
    return viewModel.connectView(view)
}