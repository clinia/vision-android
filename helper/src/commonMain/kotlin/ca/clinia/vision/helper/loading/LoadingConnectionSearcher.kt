package ca.clinia.vision.helper.loading

import ca.clinia.vision.core.Callback
import ca.clinia.vision.core.connection.ConnectionImpl
import ca.clinia.vision.core.loading.LoadingViewModel
import ca.clinia.vision.core.searcher.Debouncer
import ca.clinia.vision.core.searcher.Searcher

internal data class LoadingConnectionSearcher<R>(
    private val viewModel: LoadingViewModel,
    private val searcher: Searcher<R>,
    private val debouncer: Debouncer
) : ConnectionImpl() {

    private val eventReload: Callback<Unit> = {
        searcher.searchAsync()
    }
    private val updateIsLoading: Callback<Boolean> = {
        debouncer.debounce(searcher) {
            viewModel.isLoading.value = it
        }
    }

    override fun connect() {
        super.connect()
        viewModel.isLoading.value = searcher.isLoading.value
        viewModel.eventReload.subscribe(eventReload)
        searcher.isLoading.subscribePast(updateIsLoading)
    }

    override fun disconnect() {
        super.disconnect()
        viewModel.eventReload.unsubscribe(eventReload)
        searcher.isLoading.unsubscribe(updateIsLoading)
    }
}