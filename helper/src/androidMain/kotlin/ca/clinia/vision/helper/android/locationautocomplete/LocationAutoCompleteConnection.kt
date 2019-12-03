package ca.clinia.vision.helper.android.locationautocomplete

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import ca.clinia.vision.core.connection.Connection
import ca.clinia.vision.core.locationautocomplete.LocationAutoCompleteView
import ca.clinia.vision.core.locationautocomplete.LocationAutoCompleteViewModel
import ca.clinia.vision.core.locationautocomplete.connectView
import ca.clinia.vision.core.searcher.*


public fun <R, RP> LocationAutoCompleteViewModel.connectSearcher(
    searcher: Searcher<R>,
    searcherPlaces: SearcherPlaces<RP>,
    pagedList: List<LiveData<out PagedList<out Any>>>,
    debouncer: Debouncer = Debouncer(debounceSearchInMillis)
): Connection {
    return LocationAutoCompleteConnectionSearcherPagedList(this, searcher, searcherPlaces, pagedList, debouncer)
}

public fun <R, RP> LocationAutoCompleteConnectorPagedList<R, RP>.connectView(
    view: LocationAutoCompleteView
): Connection {
    return viewModel.connectView(view)
}