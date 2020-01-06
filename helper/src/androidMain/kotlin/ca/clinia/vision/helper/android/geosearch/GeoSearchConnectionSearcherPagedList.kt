package ca.clinia.vision.helper.android.geosearch

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import ca.clinia.search.model.search.BoundingBox
import ca.clinia.vision.core.Callback
import ca.clinia.vision.core.connection.ConnectionImpl
import ca.clinia.vision.core.geosearch.GeoSearchViewModel
import ca.clinia.vision.core.searcher.Debouncer
import ca.clinia.vision.core.searcher.Searcher
import ca.clinia.vision.helper.geosearch.GeoSearchMode

internal data class GeoSearchConnectionSearcherPagedList<R>(
    private val viewModel: GeoSearchViewModel,
    private val searcher: Searcher<R>,
    private val pagedList: List<LiveData<out PagedList<out Any>>>,
    private val geoSearchMode: GeoSearchMode,
    private val debouncer: Debouncer
)  : ConnectionImpl() {

    private val searchOnMapMove: Callback<BoundingBox?> = { boundingBox ->
        searcher.setInsideBoundingBox(boundingBox)
        debouncer.debounce(searcher) {
            pagedList.forEach {
                it.value?.dataSource?.invalidate()
            }
        }
    }

    private val searchOnSubmit: Callback<BoundingBox?> = { boundingBox ->
        searcher.setInsideBoundingBox(boundingBox)
        pagedList.forEach {
            it.value?.dataSource?.invalidate()
        }
    }

    override fun connect() {
        super.connect()
        when(geoSearchMode) {
            GeoSearchMode.OnMapMove -> viewModel.insideBoundingBox.subscribe(searchOnMapMove)
            GeoSearchMode.OnSubmit -> viewModel.eventSubmit.subscribe(searchOnSubmit)
        }
    }

    override fun disconnect() {
        super.disconnect()
        when(geoSearchMode) {
            GeoSearchMode.OnMapMove -> viewModel.insideBoundingBox.unsubscribe(searchOnMapMove)
            GeoSearchMode.OnSubmit -> viewModel.eventSubmit.unsubscribe(searchOnSubmit)
        }
    }
}