package searcher

import ca.clinia.vision.core.searcher.SearcherPlaces
import ca.clinia.vision.core.searcher.SearcherQuerySuggestions
import ca.clinia.vision.core.subscription.SubscriptionValue
import ca.clinia.vision.helper.searcher.SearcherScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MockSearcherQuerySuggestions : SearcherQuerySuggestions<Unit> {

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

    override fun searchQuerySuggestionsAsync(): Job {
        return coroutineScope.launch { searchQuerySuggestions() }.also { job = it }
    }

    override suspend fun searchQuerySuggestions() {
        searchCount++
    }

    override fun cancel() = Unit
}