package ca.clinia.vision.helper.android.searchbox

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import ca.clinia.vision.core.connection.ConnectionImpl
import ca.clinia.vision.core.searchbox.SearchBoxViewModel
import ca.clinia.vision.core.searcher.Debouncer
import ca.clinia.vision.core.searcher.Searcher
import ca.clinia.vision.core.searcher.debounceSearchInMillis
import ca.clinia.vision.helper.searchbox.SearchMode

public data class SearchBoxConnectorPagedList<R>(
    public val searcher: Searcher<R>,
    public val pagedList: List<LiveData<out PagedList<out Any>>>,
    public val viewModel: SearchBoxViewModel = SearchBoxViewModel(),
    public val searchMode: SearchMode = SearchMode.AsYouType,
    public val debouncer: Debouncer = Debouncer(debounceSearchInMillis)
) : ConnectionImpl() {

    private val connectionSearcher = viewModel.connectSearcher(searcher, pagedList, searchMode, debouncer)

    override fun connect() {
        super.connect()
        connectionSearcher.connect()
    }

    override fun disconnect() {
        super.disconnect()
        connectionSearcher.disconnect()
    }
}