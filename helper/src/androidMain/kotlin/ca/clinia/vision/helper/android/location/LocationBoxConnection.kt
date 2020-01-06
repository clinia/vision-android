package ca.clinia.vision.helper.android.location

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import ca.clinia.vision.core.connection.Connection
import ca.clinia.vision.core.location.LocationBoxView
import ca.clinia.vision.core.location.LocationBoxViewModel
import ca.clinia.vision.core.location.connectView
import ca.clinia.vision.core.searcher.*


public fun <R, RP> LocationBoxViewModel.connectSearcher(
    searcher: Searcher<R>,
    searcherPlaces: SearcherPlaces<RP>,
    pagedList: List<LiveData<out PagedList<out Any>>>,
    debouncer: Debouncer = Debouncer(debounceSearchInMillis)
): Connection {
    return LocationConnectionSearcherPagedList(this, searcher, searcherPlaces, pagedList, debouncer)
}

public fun <R, RP> LocationAutoCompleteConnectorPagedList<R, RP>.connectView(
    view: LocationBoxView
): Connection {
    return viewModel.connectView(view)
}