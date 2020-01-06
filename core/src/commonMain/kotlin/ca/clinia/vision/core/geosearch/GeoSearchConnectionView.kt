package ca.clinia.vision.core.geosearch

import ca.clinia.vision.core.Callback
import ca.clinia.vision.core.Presenter
import ca.clinia.vision.core.connection.ConnectionImpl
import ca.clinia.vision.core.searcher.Searcher


internal data class GeoSearchConnectionView<R, T>(
    private val searcher: Searcher<R>,
    private val viewModel: GeoSearchViewModel,
    private val view: GeoSearchView<T>,
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

        view.setBoundingBox(viewModel.insideBoundingBox.value)
        view.onInsideBoundingBoxChanged = (viewModel.insideBoundingBox::value::set)
        view.onInsideBoundingBoxSubmitted = {
            viewModel.insideBoundingBox.value = it
            viewModel.eventSubmit.send(it)
        }
    }

    override fun disconnect() {
        super.disconnect()

        searcher.response.unsubscribe(callback)

        view.onInsideBoundingBoxChanged = null
        view.onInsideBoundingBoxSubmitted = null
    }

}