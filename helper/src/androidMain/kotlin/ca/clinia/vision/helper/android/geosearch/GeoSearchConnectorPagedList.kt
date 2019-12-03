package ca.clinia.vision.helper.android.geosearch

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import ca.clinia.vision.core.connection.ConnectionImpl
import ca.clinia.vision.core.geosearch.GeoSearchViewModel
import ca.clinia.vision.core.searcher.Debouncer
import ca.clinia.vision.core.searcher.Searcher
import ca.clinia.vision.core.searcher.debounceSearchInMillis
import ca.clinia.vision.helper.geosearch.GeoSearchMode
import ca.clinia.vision.helper.geosearch.connectSearcher

public data class GeoSearchConnectorPagedList<R>(
    public val searcher: Searcher<R>,
    public val pagedList: List<LiveData<out PagedList<out Any>>>,
    public val viewModel: GeoSearchViewModel = GeoSearchViewModel(),
    public val geoSearchMode: GeoSearchMode = GeoSearchMode.OnSubmit,
    public val debouncer: Debouncer = Debouncer(debounceSearchInMillis)
) : ConnectionImpl() {

    private val connectionSearcher = viewModel.connectSearcher(searcher, pagedList, geoSearchMode, debouncer)

    override fun connect() {
        super.connect()
        connectionSearcher.connect()
    }

    override fun disconnect() {
        super.disconnect()
        connectionSearcher.disconnect()
    }
}