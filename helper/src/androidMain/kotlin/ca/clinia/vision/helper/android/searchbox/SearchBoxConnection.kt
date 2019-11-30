package ca.clinia.vision.helper.android.searchbox

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import ca.clinia.vision.core.connection.Connection
import ca.clinia.vision.core.searchbox.SearchBoxView
import ca.clinia.vision.core.searchbox.SearchBoxViewModel
import ca.clinia.vision.core.searchbox.connectView
import ca.clinia.vision.core.searcher.Debouncer
import ca.clinia.vision.core.searcher.Searcher
import ca.clinia.vision.core.searcher.debounceSearchInMillis
import ca.clinia.vision.helper.searchbox.SearchMode

public fun <R> SearchBoxViewModel.connectSearcher(
    searcher: Searcher<R>,
    pagedList: List<LiveData<out PagedList<out Any>>>,
    searchAsYouType: SearchMode = SearchMode.AsYouType,
    debouncer: Debouncer = Debouncer(debounceSearchInMillis)
): Connection {
    return SearchBoxConnectionSearcherPagedList(this, searcher, pagedList, searchAsYouType, debouncer)
}

public fun <R> SearchBoxConnectorPagedList<R>.connectView(
    view: SearchBoxView
): Connection {
    return viewModel.connectView(view)
}