package searcher

import ca.clinia.vision.core.searcher.Searcher
import ca.clinia.vision.core.subscription.SubscriptionValue
import ca.clinia.vision.helper.searcher.SearcherScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MockSearcher : Searcher<Unit> {

    var job: Job? = null
    var string: String? = null
    var searchCount: Int = 0

    override val isLoading = SubscriptionValue(false)
    override val error = SubscriptionValue<Throwable?>(null)
    override val response = SubscriptionValue<Unit?>(null)
    override val coroutineScope: CoroutineScope = SearcherScope(Dispatchers.Default)

    override fun setQuery(text: String?) {
        string = text
    }

    override fun searchAsync(): Job {
        return coroutineScope.launch { search() }.also { job = it }
    }

    override suspend fun search() {
        searchCount++
    }

    override fun cancel() = Unit
}