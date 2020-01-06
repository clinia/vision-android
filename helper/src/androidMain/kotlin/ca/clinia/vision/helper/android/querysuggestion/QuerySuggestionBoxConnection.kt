package ca.clinia.vision.helper.android.querysuggestion

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import ca.clinia.vision.core.connection.Connection
import ca.clinia.vision.core.querysuggestion.QuerySuggestionBoxView
import ca.clinia.vision.core.querysuggestion.QuerySuggestionViewModel
import ca.clinia.vision.core.querysuggestion.connectView
import ca.clinia.vision.core.searcher.Debouncer
import ca.clinia.vision.core.searcher.Searcher
import ca.clinia.vision.core.searcher.SearcherQuerySuggestions
import ca.clinia.vision.core.searcher.debounceSearchInMillis

public fun <R, RQS> QuerySuggestionViewModel.connectSearcher(
    searcher: Searcher<R>,
    searcherQuerySuggestions: SearcherQuerySuggestions<RQS>,
    pagedList: List<LiveData<out PagedList<out Any>>>,
    debouncer: Debouncer = Debouncer(debounceSearchInMillis)
): Connection {
    return QuerySuggestionConnectionSearcherPagedList(this, searcher, searcherQuerySuggestions, pagedList, debouncer)
}

public fun <R, RQS> QuerySuggestionConnectorPagedList<R, RQS>.connectView(
    view: QuerySuggestionBoxView
): Connection {
    return viewModel.connectView(view)
}