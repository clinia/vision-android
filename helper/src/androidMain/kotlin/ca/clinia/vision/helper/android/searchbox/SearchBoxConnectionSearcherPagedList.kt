package ca.clinia.vision.helper.android.searchbox

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import ca.clinia.vision.core.Callback
import ca.clinia.vision.core.connection.ConnectionImpl
import ca.clinia.vision.core.searchbox.SearchBoxViewModel
import ca.clinia.vision.core.searcher.Debouncer
import ca.clinia.vision.core.searcher.Searcher
import ca.clinia.vision.helper.searchbox.SearchMode

internal data class     SearchBoxConnectionSearcherPagedList<R>(
    private val viewModel: SearchBoxViewModel,
    private val searcher: Searcher<R>,
    private val pagedList: List<LiveData<out PagedList<out Any>>>,
    private val searchMode: SearchMode,
    private val debouncer: Debouncer
) : ConnectionImpl() {

    private val searchAsYouType: Callback<String?> = { query ->
        searcher.setQuery(query)
        debouncer.debounce(searcher) {
            pagedList.forEach {
                it.value?.dataSource?.invalidate()
            }
        }
    }
    private val searchOnSubmit: Callback<String?> = { query ->
        searcher.setQuery(query)
        pagedList.forEach {
            it.value?.dataSource?.invalidate()
        }
    }

    override fun connect() {
        super.connect()
        when (searchMode) {
            SearchMode.AsYouType -> viewModel.query.subscribe(searchAsYouType)
            SearchMode.OnSubmit -> viewModel.eventSubmit.subscribe(searchOnSubmit)
        }
    }

    override fun disconnect() {
        super.disconnect()
        when (searchMode) {
            SearchMode.AsYouType -> viewModel.query.unsubscribe(searchAsYouType)
            SearchMode.OnSubmit -> viewModel.eventSubmit.unsubscribe(searchOnSubmit)
        }
    }
}