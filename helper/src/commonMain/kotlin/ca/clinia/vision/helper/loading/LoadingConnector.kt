package ca.clinia.vision.helper.loading

import ca.clinia.vision.core.connection.ConnectionImpl
import ca.clinia.vision.core.loading.LoadingViewModel
import ca.clinia.vision.core.searcher.Debouncer
import ca.clinia.vision.core.searcher.Searcher
import ca.clinia.vision.core.searcher.debounceLoadingInMillis

public data class LoadingConnector<R>(
    public val searcher: Searcher<R>,
    public val viewModel: LoadingViewModel = LoadingViewModel(),
    public val debouncer: Debouncer = Debouncer(debounceLoadingInMillis)
) : ConnectionImpl() {

    private val connectionSearcher = viewModel.connectSearcher(searcher, debouncer)

    override fun connect() {
        super.connect()
        connectionSearcher.connect()
    }

    override fun disconnect() {
        super.disconnect()
        connectionSearcher.disconnect()
    }
}