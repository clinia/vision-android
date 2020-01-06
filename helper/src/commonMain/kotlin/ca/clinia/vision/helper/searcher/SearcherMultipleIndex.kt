package ca.clinia.vision.helper.searcher

import ca.clinia.search.client.ClientSearch
import ca.clinia.search.model.multipleindex.IndexQuery
import ca.clinia.search.model.response.ResponseSearches
import ca.clinia.search.model.search.BoundingBox
import ca.clinia.search.model.search.Point
import ca.clinia.search.transport.RequestOptions
import ca.clinia.vision.core.searcher.Searcher
import ca.clinia.vision.core.searcher.Sequencer
import ca.clinia.vision.core.subscription.SubscriptionValue
import kotlinx.coroutines.*

public class SearcherMultipleIndex(
    public val client: ClientSearch,
    public val queries: List<IndexQuery>,
    public val requestOptions: RequestOptions? = null,
    override val coroutineScope: CoroutineScope = SearcherScope()
) : Searcher<ResponseSearches> {

    internal val sequencer = Sequencer()

    override val isLoading = SubscriptionValue(false)
    override val error = SubscriptionValue<Throwable?>(null)
    override val response = SubscriptionValue<ResponseSearches?>(null)

    private val options = requestOptions.withUserAgent()
    private val exceptionHandler = SearcherExceptionHandler(this)

    override fun setQuery(text: String?) {
        queries.forEach { it.query.query = text }
    }

    //region GeoSearch

    override fun setLocation(text: String?) {
        queries.forEach {
            it.query.location = text
            it.query.insideBoundingBox = null
            it.query.aroundLatLng = null
        }
    }

    override fun setInsideBoundingBox(insideBoundingBox: BoundingBox?) {
        queries.forEach {
            it.query.location = null
            it.query.insideBoundingBox = insideBoundingBox
            it.query.aroundLatLng = null
        }
    }

    override fun setAroundLatLng(aroundLatLng: Point?) {
        queries.forEach {
            it.query.location = null
            it.query.insideBoundingBox = null
            it.query.aroundLatLng = aroundLatLng
        }
    }

    //endregion

    override fun searchAsync(): Job {
        return coroutineScope.launch(exceptionHandler) {
            isLoading.value = true
            response.value = withContext(Dispatchers.Default) { search() }
            isLoading.value = false
        }.also {
            sequencer.addOperation(it)
        }
    }

    override suspend fun search(): ResponseSearches {
        return client.multipleQueries(queries, options)
    }

    override fun cancel() {
        sequencer.cancelAll()
    }

}