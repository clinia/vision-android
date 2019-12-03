package ca.clinia.vision.helper.android.locationautocomplete

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import ca.clinia.vision.core.connection.ConnectionImpl
import ca.clinia.vision.core.locationautocomplete.LocationAutoCompleteViewModel
import ca.clinia.vision.core.searcher.*

public data class LocationAutoCompleteConnectorPagedList<R, RP>(
    public val searcher: Searcher<R>,
    public val searcherPlaces: SearcherPlaces<RP>,
    public val pagedList: List<LiveData<out PagedList<out Any>>>,
    public val viewModel: LocationAutoCompleteViewModel = LocationAutoCompleteViewModel(),
    public val debouncer: Debouncer = Debouncer(debounceSearchInMillis)
) : ConnectionImpl() {

    private val connectionSearcher = viewModel.connectSearcher(searcher, searcherPlaces, pagedList, debouncer)

    override fun connect() {
        super.connect()
        connectionSearcher.connect()
    }

    override fun disconnect() {
        super.disconnect()
        connectionSearcher.disconnect()
    }
}