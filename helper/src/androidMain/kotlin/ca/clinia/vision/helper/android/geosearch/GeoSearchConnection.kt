package ca.clinia.vision.helper.android.geosearch

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import ca.clinia.vision.core.connection.Connection
import ca.clinia.vision.core.geosearch.GeoSearchViewModel
import ca.clinia.vision.core.searcher.Debouncer
import ca.clinia.vision.core.searcher.Searcher
import ca.clinia.vision.core.searcher.debounceSearchInMillis
import ca.clinia.vision.helper.geosearch.GeoSearchMode

public fun <R> GeoSearchViewModel.connectSearcher(
    searcher: Searcher<R>,
    pagedList: List<LiveData<out PagedList<out Any>>>,
    searchOnMapMove: GeoSearchMode = GeoSearchMode.OnMapMove,
    debouncer: Debouncer = Debouncer(debounceSearchInMillis)
): Connection {
    return GeoSearchConnectionSearcherPagedList(this, searcher, pagedList, searchOnMapMove, debouncer)
}