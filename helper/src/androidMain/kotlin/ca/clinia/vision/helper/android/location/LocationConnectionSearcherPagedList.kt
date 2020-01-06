package ca.clinia.vision.helper.android.location

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import ca.clinia.vision.core.Callback
import ca.clinia.vision.core.connection.ConnectionImpl
import ca.clinia.vision.core.location.LocationBoxViewModel
import ca.clinia.vision.core.searcher.Debouncer
import ca.clinia.vision.core.searcher.Searcher
import ca.clinia.vision.core.searcher.SearcherPlaces

internal data class LocationConnectionSearcherPagedList<R, RP>(
    private val viewModel: LocationBoxViewModel,
    private val searcher: Searcher<R>,
    private val searcherPlaces: SearcherPlaces<RP>,
    private val pagedList: List<LiveData<out PagedList<out Any>>>,
    private val debouncer: Debouncer
) : ConnectionImpl() {

    private val searchForPlaces: Callback<String?> = { query ->
        searcherPlaces.setQuery(query)
        debouncer.debounce(searcherPlaces) {
            searchPlacesAsync()
        }
    }
    private val searchOnSubmit: Callback<String?> = { location ->
        searcher.setLocation(location)
        pagedList.forEach {
            it.value?.dataSource?.invalidate()
        }
    }

    override fun connect() {
        super.connect()
        viewModel.query.subscribe(searchForPlaces)
        viewModel.eventSubmit.subscribe(searchOnSubmit)
    }

    override fun disconnect() {
        super.disconnect()

        viewModel.query.unsubscribe(searchForPlaces)
        viewModel.eventSubmit.unsubscribe(searchOnSubmit)
    }
}