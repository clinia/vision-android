package ca.clinia.vision.helper.geosearch

import ca.clinia.vision.core.connection.ConnectionImpl
import ca.clinia.vision.core.geosearch.GeoSearchViewModel
import ca.clinia.vision.core.searcher.Debouncer
import ca.clinia.vision.core.searcher.Searcher
import ca.clinia.vision.core.searcher.debounceSearchInMillis

public data class GeoSearchConnector<R>(
    public val searcher: Searcher<R>,
    public val viewModel: GeoSearchViewModel = GeoSearchViewModel(),
    public val geoSearchMode: GeoSearchMode = GeoSearchMode.OnSubmit,
    public val debouncer: Debouncer = Debouncer(debounceSearchInMillis)
) : ConnectionImpl() {

    private val connectionSearcher = viewModel.connectSearcher(searcher, geoSearchMode, debouncer)

    override fun connect() {
        super.connect()
        connectionSearcher.connect()
    }

    override fun disconnect() {
        super.disconnect()
        connectionSearcher.disconnect()
    }
}