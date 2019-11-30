package ca.clinia.vision.core.hits

import ca.clinia.vision.core.Callback
import ca.clinia.vision.core.Presenter
import ca.clinia.vision.core.connection.ConnectionImpl
import ca.clinia.vision.core.searcher.Searcher

internal data class HitsConnectionView<R, T>(
    private val searcher: Searcher<R>,
    private val view: HitsView<T>,
    private val presenter: Presenter<R, List<T>>
) : ConnectionImpl() {

    private val callback: Callback<R?> = { response ->
        if (response != null) {
            view.setRecords(presenter(response))
        }
    }

    override fun connect() {
        super.connect()
        searcher.response.subscribe(callback)
    }

    override fun disconnect() {
        super.disconnect()
        searcher.response.unsubscribe(callback)
    }
}